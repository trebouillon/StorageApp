package de.boettcher.storage.repository

import android.util.Log
import com.google.gson.Gson
import de.boettcher.storage.api.IStorageClient
import de.boettcher.storage.interactor.barcode.BarcodeData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ScanRepository @Inject constructor(private val storageClient: IStorageClient) :
    IScanRepository {

    private val gson = Gson()

    override fun send(barcodeData: BarcodeData): Single<String> {
        return Single.fromCallable {
            sendPayload(barcodeData)
        }.subscribeOn(Schedulers.io())
    }

    private fun sendPayload(barcodeData: BarcodeData): String {
//        storageClient.send(gson.toJson(barcodeData))
        val payload = gson.toJson(barcodeData)
        Log.d("ScanRepository", payload)
        return payload
    }
}
