package de.boettcher.storage.interactor

import de.boettcher.storage.model.BarcodeData
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

// TODO("name will change later")
interface ISendBarcodeInteractor {

    fun send(barcodeData: BarcodeData): Single<String>

    fun saveUserToken(token: String): Completable

    fun removeUserToken(): Completable

    fun getUserToken(): Maybe<String>

}
