package de.boettcher.storage.store

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

interface IStore<S : IState> {

    fun subscribe(consumer: Consumer<S>, scheduler: Scheduler): Disposable

    fun stop()

    fun dispose()

}
