package de.boettcher.storage.interactor.barcode

import de.boettcher.storage.model.BarcodeData
import de.boettcher.storage.repository.IScanRepository
import io.reactivex.Single
import javax.inject.Inject

class SendBarcodeInteractor @Inject constructor(
    private val scanRepository: IScanRepository
) : ISendBarcodeInteractor {

    override fun send(barcodeData: BarcodeData): Single<String> {
        return scanRepository.send(barcodeData)
    }
}
