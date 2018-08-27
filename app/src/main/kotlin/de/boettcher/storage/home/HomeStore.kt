package de.boettcher.storage.home

import de.boettcher.storage.interactor.auth.IGetUserTokenInteractor
import de.boettcher.storage.store.BaseStore
import io.reactivex.Observable
import javax.inject.Inject

class HomeStore @Inject constructor(
    private val getUserTokenInteractor: IGetUserTokenInteractor
) : BaseStore<HomeState>(HomeState.Default), IHomeStore {

    override fun sendBarcode(value: String) {
        val sendBarCodeState = object : RecreateStateFunction<HomeState> {

            override fun recreate(stateProvider: StateProvider<HomeState>): Observable<HomeState> {

                val currentState = stateProvider.currentState

                return when (currentState) {
                    is HomeState.Scan -> {

                        getUserTokenInteractor.getUserToken().isEmpty.toObservable()
                            .map { loggedIn ->
                                when (loggedIn) {
                                    true -> HomeState.Default
                                    else -> HomeState.Error
                                }
                            }

                    }
                    is HomeState.Error -> Observable.just(currentState)
                    is HomeState.Default -> Observable.just(currentState)
                }

            }

        }

        buildState(sendBarCodeState, getErrorState())
    }

    private fun getErrorState(): RecreateStateFunctionOnError<HomeState> {
        return object : RecreateStateFunctionOnError<HomeState> {

            override fun recreate(
                stateProvider: StateProvider<HomeState>,
                throwable: Throwable
            ): Observable<HomeState> {
                return Observable.just(HomeState.Error)
            }

        }
    }
}
