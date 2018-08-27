package de.boettcher.storage.home

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeStore: IHomeStore,
    private val navigator: IHomeNavigator
) {

    private val disposable = CompositeDisposable()

    fun onCreate() {
        disposable.addAll(homeStore.subscribe(Consumer {
            Log.d("HomeViewModel", "state is: " + it.toString())
        }, AndroidSchedulers.mainThread()))
    }

    fun take() {
        navigator.startTakeScan()
    }

    fun put() {
        navigator.startPutScan()
    }

    fun onLoginClicked() {
        navigator.login()
    }

}
