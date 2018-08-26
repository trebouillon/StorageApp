package de.boettcher.storage.interactor.auth

import io.reactivex.Completable

interface ISaveUserTokenInteractor {

    fun saveUserToken(token: String): Completable

}
