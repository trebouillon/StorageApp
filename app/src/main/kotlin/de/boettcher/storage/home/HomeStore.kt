package de.boettcher.storage.home

import de.boettcher.storage.interactor.auth.IGetUserTokenInteractor
import de.boettcher.storage.interactor.barcode.ISendBarcodeInteractor
import de.boettcher.storage.store.BaseStore
import io.reactivex.Observable
import javax.inject.Inject

class HomeStore @Inject constructor(
    private val sendBarcodeInteractor: ISendBarcodeInteractor,
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
                                    else -> HomeState.Error(ErrorType.AUTHENTICATION_FAILED)
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
                return Observable.just(HomeState.Error(ErrorType.GENERAL))
            }

        }
    }
}

//            val logoutErrorState = object : BaseStore.RecreateStateFunctionOnError<HomeState> {
//
//            }
//
//            buildState(logoutState, logoutErrorState)

//    if (codes.size == 1) {
//        sendBarcodeInteractor.getUserToken().doOnSuccess {
//            sendBarcodeInteractor.send(createBarcode(it, *codes))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(onSuccess(), onError())
//        }.observeOn(AndroidSchedulers.mainThread())
//            .doOnComplete { viewStateSubject.onNext(ScanViewState.NoToken) }
//            .subscribe()
//    } else {
//        sendBarcodeInteractor.send(createBarcode(codes = *codes))
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(onSuccess(), onError())
//    }
