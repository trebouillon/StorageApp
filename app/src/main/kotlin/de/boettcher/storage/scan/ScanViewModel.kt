package de.boettcher.storage.scan

import de.boettcher.storage.interactor.ISendBarcodeInteractor
import de.boettcher.storage.model.Barcode
import de.boettcher.storage.model.BarcodeData
import de.boettcher.storage.model.ScanType
import de.boettcher.storage.model.StorageType
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ScanViewModel @Inject constructor(private val sendBarcodeInteractor: ISendBarcodeInteractor) {

    private val viewStateSubject = BehaviorSubject.create<ScanViewState>()

    fun observe(): Observable<ScanViewState> {
        return viewStateSubject.startWith(ScanViewState.None)
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    fun sendBarcode(code: String) {
        sendBarcodeInteractor.send(createBarcode(code)).observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess(), onError())
    }

    private fun onError(): Consumer<in Throwable>? =
        Consumer {
            viewStateSubject.onNext(
                ScanViewState.Error(
                    it.message ?: "unknown error"
                )
            )
        }

    private fun onSuccess(): Consumer<String> =
        Consumer { viewStateSubject.onNext(ScanViewState.Success(it)) }

    private fun createBarcode(code: String): BarcodeData {
        return BarcodeData(
            scanType = ScanType.TAKE,
            userId = "tre",
            barcodes = listOf(
                Barcode(
                    storageType = StorageType.ITEM,
                    code = code.toLong()
                )
            )
        )
    }

}
