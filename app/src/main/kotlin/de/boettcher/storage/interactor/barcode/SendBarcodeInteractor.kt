package de.boettcher.storage.interactor.barcode

import de.boettcher.storage.model.BarcodeData
import de.boettcher.storage.repository.IBarcodeRepository
import io.reactivex.Single
import javax.inject.Inject

class SendBarcodeInteractor @Inject constructor(
    private val barcodeRepository: IBarcodeRepository
) : ISendBarcodeInteractor {

    override fun send(barcodeData: BarcodeData): Single<String> {
        return barcodeRepository.send(barcodeData)
    }
}
