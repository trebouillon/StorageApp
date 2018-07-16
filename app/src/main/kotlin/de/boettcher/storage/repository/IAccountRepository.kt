package de.boettcher.storage.repository

import io.reactivex.Completable
import io.reactivex.Maybe

interface IAccountRepository {

    fun getUserToken(): Maybe<String>

    fun saveUserToken(token: String): Completable

    fun removeUserToken(): Completable

}
