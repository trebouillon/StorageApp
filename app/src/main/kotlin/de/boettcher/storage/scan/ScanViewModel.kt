package de.boettcher.storage.scan

import android.content.res.Resources
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Handler
import android.os.Looper
import android.support.annotation.StringRes
import de.boettcher.storage.R
import de.boettcher.storage.model.BoundingBox
import de.boettcher.storage.model.ErrorType
import de.boettcher.storage.utils.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
    val isError = ObservableBoolean(false)
    val scanHint = ObservableField<String>(TextUtils.EMPTY)
    val errorMessage = ObservableField<String>(TextUtils.EMPTY)
    val isBarcodeScanned = ObservableBoolean(false)
    val scannedBarcodeText = ObservableField<String>(TextUtils.EMPTY)
    private val disposable = CompositeDisposable()

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
        disposable.add(
            scanStore.subscribe(Consumer {

                when (it) {
                    is ScanState.Idle -> updateIdleState()
                    is ScanState.Take -> updateTakeState()
                    is ScanState.Put -> updatePutState(it.barcode)
                    is ScanState.Error -> updateErrorState(it)
                    is ScanState.Finish -> scanNavigator.close()
                }

            }, AndroidSchedulers.mainThread())
        )
    }

    private fun updateErrorState(it: ScanState.Error) {
        isError.set(true)
        isOperational.set(false)

        @StringRes val message = when (it.errorType) {
            ErrorType.GENERAL -> R.string.scan_error_general
            ErrorType.NO_CONNECTION -> R.string.scan_error_network
            ErrorType.AUTHENTICATION_FAILED -> R.string.scan_error_authentication
        }
        errorMessage.set(resources.getString(message))
    }

    private fun updateIdleState() {
        isOperational.set(false)
        isError.set(false)
    }

    private fun updatePutState(barcode: String?) {
        isOperational.set(true)
        isError.set(false)
        val scans = when (barcode) {
            null -> 1
            else -> 2
        }
        scanHint.set(resources.getString(R.string.scan_hint, scans, 2))
    }

    private fun updateTakeState() {
        isOperational.set(true)
        isError.set(false)
        scanHint.set(resources.getString(R.string.scan_hint, 1, 1))
    }

    fun displayBoundingBox(boundingBox: BoundingBox) {
        barcodeArea.set(boundingBox)
        isAcceptBarcode.set(true)
    }

    fun sendBarcode() {
        isBarcodeScanned.set(true)
        barcodeArea.get()?.let {
            scannedBarcodeText.set(resources.getString(R.string.scan_accepted, it.getValue()))
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

            isBarcodeScanned.set(false)
            barcodeArea.get()?.let {
                scanStore.sendBarcode(it.getValue())

            }
        }, 1500)
    }

    fun onDestroy() {
        disposable.dispose()
    }

    fun close() {
        scanNavigator.close()
    }

}
