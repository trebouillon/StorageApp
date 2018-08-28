package de.boettcher.storage.repository

import com.google.gson.Gson
import de.boettcher.storage.api.IStorageClient
import de.boettcher.storage.model.BarcodeData
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

    private fun sendPayload(barcodeData: BarcodeData) = storageClient.send(gson.toJson(barcodeData))
}
