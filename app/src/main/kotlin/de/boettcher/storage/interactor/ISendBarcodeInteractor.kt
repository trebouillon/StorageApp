package de.boettcher.storage.interactor

import de.boettcher.storage.model.BarcodeData
import io.reactivex.Single

interface ISendBarcodeInteractor {

    fun send(barcodeData: BarcodeData): Single<String>

}
