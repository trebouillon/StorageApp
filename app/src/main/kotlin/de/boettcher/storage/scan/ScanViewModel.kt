package de.boettcher.storage.scan

import javax.inject.Inject

class ScanViewModel @Inject constructor(private val scanNavigator: IScanNavigator) {

    fun onSurfaceCreated() {
        scanNavigator.onSurfaceCreated()
    }

    fun onSurfaceDestroyed() {
        scanNavigator.onSurfaceDestroyed()
    }

    fun sendBarcodeData(value: String) {
        // todo send data
    }

}
