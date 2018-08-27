package de.boettcher.storage.interactor.barcode

import io.reactivex.Single

interface ISendBarcodeInteractor {

    fun send(barcodeData: BarcodeData): Single<String>

}
