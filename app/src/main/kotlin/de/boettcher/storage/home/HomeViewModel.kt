package de.boettcher.storage.home

import javax.inject.Inject

class HomeViewModel @Inject constructor(private val navigator: IHomeNavigator) {

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
