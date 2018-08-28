package de.boettcher.storage.scan

import android.content.res.Resources
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import de.boettcher.storage.R
import de.boettcher.storage.model.BoundingBox
import de.boettcher.storage.utils.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

class ScanViewModel @Inject constructor(
    private val scanStore: IScanStore,
    private val scanNavigator: IScanNavigator,
    private val resources: Resources
) {

    val barcodeArea = ObservableField<BoundingBox>()
    val isOperational = ObservableBoolean(false)
    val isAcceptBarcode = ObservableBoolean(false)
    val scanHint = ObservableField<String>(TextUtils.EMPTY)

    fun onSurfaceCreated() {
        scanNavigator.onSurfaceCreated()
    }

    fun onSurfaceDestroyed() {
        scanNavigator.onSurfaceDestroyed()
    }

    fun startTake() {
        initializeStore()
        scanStore.take()
    }

    fun startPut() {
        initializeStore()
        scanStore.put()
    }

    private fun initializeStore() {
        scanStore.subscribe(Consumer {

            when (it) {
                is ScanState.Idle -> isOperational.set(false)
                is ScanState.Take -> updateTakeState()
                is ScanState.Put -> updatePutState(it.barcode)
                is ScanState.Error -> isOperational.set(false)
                is ScanState.Finish -> scanNavigator.onBarcodeSend()
            }

        }, AndroidSchedulers.mainThread())
    }

    private fun updatePutState(barcode: String?) {
        isOperational.set(true)
        val scans = when (barcode) {
            null -> 1
            else -> 2
        }
        scanHint.set(resources.getString(R.string.scan_hint, scans, 2))
    }

    private fun updateTakeState() {
        isOperational.set(true)
        scanHint.set(resources.getString(R.string.scan_hint, 1, 1))
    }

    fun displayBoundingBox(boundingBox: BoundingBox) {
        barcodeArea.set(boundingBox)
        isAcceptBarcode.set(true)
    }

    fun sendBarcode() {
        barcodeArea.get()?.let {
            scanStore.sendBarcode(it.getValue())
        }
    }

}
