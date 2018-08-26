package de.boettcher.storage.interactor.barcode

import de.boettcher.storage.model.BarcodeData
import io.reactivex.Single

interface ISendBarcodeInteractor {

    fun send(barcodeData: BarcodeData): Single<String>

}
