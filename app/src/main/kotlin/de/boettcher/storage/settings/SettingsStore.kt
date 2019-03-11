package de.boettcher.storage.settings

import de.boettcher.storage.interactor.settings.Endpoint
import de.boettcher.storage.interactor.settings.IUpdateEndpointInteractor
import de.boettcher.storage.store.BaseStore
import de.boettcher.storage.store.IStore
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ISettingsStore : IStore<SettingsState> {

    fun updateEndpoint(endpoint: Endpoint)

    fun init()

}

class SettingsStore @Inject constructor(private val updateEndpointInteractor: IUpdateEndpointInteractor) :
    ISettingsStore, BaseStore<SettingsState>(SettingsState.Idle) {

    override fun init() {
        buildState(initState(), onError())
    }

    private fun initState() = object : RecreateStateFunction<SettingsState> {

        override fun recreate(stateProvider: StateProvider<SettingsState>): Observable<SettingsState> =
            Observable.fromCallable<SettingsState> {
                val endpoint = updateEndpointInteractor.getEndpoint()
                SettingsState.Default(endpoint)
            }

    }

    override fun updateEndpoint(endpoint: Endpoint) =
        buildState(updateEndpointState(endpoint), onError())

    private fun updateEndpointState(endpoint: Endpoint) =
        object : RecreateStateFunction<SettingsState> {

            override fun recreate(stateProvider: StateProvider<SettingsState>): Observable<SettingsState> =
                Observable.fromCallable<SettingsState> {
                    updateEndpointInteractor.update(endpoint)
                    stateProvider.currentState
                }.startWith(SettingsState.Success)
                    .subscribeOn(Schedulers.io())

        }

    private fun onError() = object : RecreateStateFunctionOnError<SettingsState> {

        override fun recreate(
            stateProvider: StateProvider<SettingsState>,
            throwable: Throwable
        ): Observable<SettingsState> =
            Observable.just(stateProvider.currentState).startWith(SettingsState.Error)
    }

}
