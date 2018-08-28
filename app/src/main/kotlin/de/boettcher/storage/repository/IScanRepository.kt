package de.boettcher.storage.repository

import de.boettcher.storage.model.BarcodeData
import io.reactivex.Single

interface IScanRepository {

    fun send(barcodeData: BarcodeData): Single<String>

}
