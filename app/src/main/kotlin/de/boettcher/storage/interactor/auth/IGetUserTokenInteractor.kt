package de.boettcher.storage.interactor.auth

import io.reactivex.Maybe

interface IGetUserTokenInteractor {

    fun getUserToken(): Maybe<String>

}
