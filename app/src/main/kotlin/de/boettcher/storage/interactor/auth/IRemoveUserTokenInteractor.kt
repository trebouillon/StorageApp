package de.boettcher.storage.interactor.auth

import io.reactivex.Completable

interface IRemoveUserTokenInteractor {

    fun removeUserToken(): Completable

}
