package de.boettcher.storage.scan

import android.databinding.ObservableField
import de.boettcher.storage.model.BoundingBox
import javax.inject.Inject

class ScanViewModel @Inject constructor(private val scanNavigator: IScanNavigator) {

    val barcodeArea = ObservableField<BoundingBox>()

    fun onSurfaceCreated() {
        scanNavigator.onSurfaceCreated()
    }

    fun onSurfaceDestroyed() {
        scanNavigator.onSurfaceDestroyed()
    }

    fun sendBarcode(boundingBox: BoundingBox) {
        barcodeArea.set(boundingBox)
        // todo send data
    }

}
