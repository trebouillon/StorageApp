package de.boettcher.storage.interactor.auth

import de.boettcher.storage.repository.IAccountRepository
import io.reactivex.Completable
import javax.inject.Inject

class SaveUserTokenInteractor @Inject constructor(private val accountRepository: IAccountRepository) :
    ISaveUserTokenInteractor {

    override fun saveUserToken(token: String): Completable {
        return accountRepository.saveUserToken(token)
    }

}
