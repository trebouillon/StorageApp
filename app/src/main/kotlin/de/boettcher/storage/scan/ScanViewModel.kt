package de.boettcher.storage.scan

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import de.boettcher.storage.model.BoundingBox
import de.boettcher.storage.model.ScanType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

class ScanViewModel @Inject constructor(
    private val scanStore: IScanStore,
    private val scanNavigator: IScanNavigator
) {

    val barcodeArea = ObservableField<BoundingBox>()
    val isOperational = ObservableBoolean(false)

    fun onSurfaceCreated() {
        scanNavigator.onSurfaceCreated()
    }

    fun onSurfaceDestroyed() {
        scanNavigator.onSurfaceDestroyed()
    }

    fun start(scanType: ScanType) {
        scanStore.subscribe(Consumer {

            when (it) {
                is ScanState.Idle -> isOperational.set(false)
                is ScanState.Take -> isOperational.set(true)
                is ScanState.Put -> isOperational.set(true)
                is ScanState.Error -> isOperational.set(false)
                is ScanState.Finish -> scanNavigator.finish()
            }

        }, AndroidSchedulers.mainThread())

        when (scanType) {
            ScanType.TAKE -> scanStore.take()
            ScanType.PUT -> scanStore.put()
        }
    }

    fun sendBarcode(boundingBox: BoundingBox) {
        barcodeArea.set(boundingBox)
        scanStore.sendBarcode(boundingBox.getValue())
    }

}
