package de.boettcher.storage.interactor.auth

import de.boettcher.storage.repository.IAccountRepository
import io.reactivex.Completable
import javax.inject.Inject

class RemoveUserTokenInteractor @Inject constructor(private val accountRepository: IAccountRepository) :
    IRemoveUserTokenInteractor {

    override fun removeUserToken(): Completable {
        return accountRepository.removeUserToken()
    }

}
