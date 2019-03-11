package de.boettcher.storage.repository

import de.boettcher.storage.persistence.IPersistenceService
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AccountRepository @Inject constructor(private val persistenceService: IPersistenceService) :
    IAccountRepository {

    companion object {
        private const val KEY_TOKEN = "userToken"
    }

    override fun getUserToken(): Maybe<String> {
        return Maybe.create<String> {
            val userToken = persistenceService.get(KEY_TOKEN)
            when (userToken) {
                null -> it.onComplete()
                else -> it.onSuccess(userToken)
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun saveUserToken(token: String): Completable {
        return Completable.fromRunnable {
            persistenceService.put(KEY_TOKEN, token)
        }.subscribeOn(Schedulers.io())
    }

    override fun removeUserToken(): Completable {
        return Completable.fromRunnable {
            persistenceService.remove(KEY_TOKEN)
        }.subscribeOn(Schedulers.io())
    }
}
