package de.boettcher.storage.store

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

open class BaseStore<S : IState>(initialState: S) : IStore<S> {

    private val compositeDisposable = CompositeDisposable()
    private val stateHolder = BehaviorSubject.createDefault(initialState).toSerialized()

    override fun subscribe(consumer: Consumer<S>, scheduler: Scheduler): Disposable {
        return stateHolder.observeOn(scheduler).subscribe(consumer)
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

    protected fun buildState(
        function: RecreateStateFunction<S>,
        functionOnError: RecreateStateFunctionOnError<S>
    ) {
        val disposable =
            function.recreate(stateProvider(stateHolder))
                .onErrorResumeNext(handleError(functionOnError))
                .subscribe(this::notifyNewState)
        compositeDisposable.add(disposable)
    }

    private fun handleError(functionOnError: RecreateStateFunctionOnError<S>): (Throwable) -> Observable<S> {
        return { throwable: Throwable ->
            buildObservableForError(
                throwable,
                functionOnError
            )
        }
    }

    private fun buildObservableForError(
        throwable: Throwable,
        functionOnError: RecreateStateFunctionOnError<S>
    ): Observable<S> {
        return functionOnError.recreate(stateProvider(stateHolder), throwable)
    }

    private fun <S : IState> stateProvider(subject: Subject<S>): StateProvider<S> {
        return object : StateProvider<S> {
            override val currentState: S
                get() = subject.firstOrError().blockingGet()
        }
    }

    private fun notifyNewState(newState: S) {
        stateHolder.onNext(newState)
    }

    interface RecreateStateFunction<S : IState> {
        fun recreate(stateProvider: StateProvider<S>): Observable<S>
    }

    interface RecreateStateFunctionOnError<S : IState> {
        fun recreate(stateProvider: StateProvider<S>, throwable: Throwable): Observable<S>
    }

    interface StateProvider<S : IState> {
        val currentState: S
    }

}
