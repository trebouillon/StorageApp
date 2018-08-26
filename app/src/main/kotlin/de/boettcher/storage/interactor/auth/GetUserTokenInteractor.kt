package de.boettcher.storage.interactor.auth

import de.boettcher.storage.repository.IAccountRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetUserTokenInteractor @Inject constructor(private val accountRepository: IAccountRepository) :

    IGetUserTokenInteractor {
    override fun getUserToken(): Maybe<String> {
        return accountRepository.getUserToken()
    }
    
}
