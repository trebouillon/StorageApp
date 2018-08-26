package de.boettcher.storage.home

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeStore: IHomeStore,
    private val navigator: HomeNavigator
) {

    private val disposable = CompositeDisposable()

    fun onCreate() {
        disposable.addAll(homeStore.subscribe(Consumer {
            Log.d("HomeViewModel", "state is: " + it.toString())
        }, AndroidSchedulers.mainThread()))
    }

    fun startScan() {
        navigator.scanBarcode()
    }

    fun onLoginClicked() {
        navigator.login()
    }

    fun sendBarcode(value: String) {
        homeStore.sendBarcode(value)
    }

}
