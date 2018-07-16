package de.boettcher.storage.interactor

import de.boettcher.storage.model.BarcodeData
import de.boettcher.storage.repository.IAccountRepository
import de.boettcher.storage.repository.IBarcodeRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class SendBarcodeInteractor @Inject constructor(
    private val barcodeRepository: IBarcodeRepository,
    private val accountRepository: IAccountRepository
) : ISendBarcodeInteractor {

    override fun saveUserToken(token: String): Completable {
        return accountRepository.saveUserToken(token)
    }

    override fun removeUserToken(): Completable {
        return accountRepository.removeUserToken()
    }

    override fun getUserToken(): Maybe<String> {
        return accountRepository.getUserToken()
    }

    override fun send(barcodeData: BarcodeData): Single<String> {
        return barcodeRepository.send(barcodeData)
    }
}
