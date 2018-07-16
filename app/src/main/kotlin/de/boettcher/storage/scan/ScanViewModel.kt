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

    // TODO("will be cleaned up later - just a quick solution")
    fun sendBarcode(vararg codes: String) {
        if (codes.size == 1) {
            sendBarcodeInteractor.getUserToken().doOnSuccess {
                sendBarcodeInteractor.send(createBarcode(it, *codes))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onSuccess(), onError())
            }.observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { viewStateSubject.onNext(ScanViewState.NoToken) }
                .subscribe()
        } else {
            sendBarcodeInteractor.send(createBarcode(codes = *codes))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess(), onError())
        }
    }

    fun saveUserToken(token: String) {
        sendBarcodeInteractor.saveUserToken(token).observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { viewStateSubject.onNext(ScanViewState.TokenSaved) }
            .subscribe()
    }

    // TODO("remove")
    fun resetState() {
        viewStateSubject.onNext(ScanViewState.None)
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

    private fun createBarcode(userToken: String? = null, vararg codes: String): BarcodeData {
        val scanType = when (codes.size) {
            1 -> ScanType.TAKE
            2 -> ScanType.PUT
            else -> throw IllegalStateException("invalid size")
        }

        val barcodes = codes.map {
            Barcode(
                storageType = checkStorageType(it),
                code = it.toLong()
            )
        }

        return BarcodeData(
            scanType = scanType,
            userId = userToken,
            barcodes = barcodes
        )
    }

    // TODO("implement validator")
    private fun checkStorageType(code: String): StorageType {
        if (code.startsWith("0") || code.startsWith("1")) return StorageType.ITEM
        return StorageType.STORE
    }

}
