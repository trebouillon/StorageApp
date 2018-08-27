package de.boettcher.storage.repository

import de.boettcher.storage.interactor.barcode.BarcodeData
import io.reactivex.Single

interface IScanRepository {

    fun send(barcodeData: BarcodeData): Single<String>

}
