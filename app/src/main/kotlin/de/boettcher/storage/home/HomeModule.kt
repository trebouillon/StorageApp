package de.boettcher.storage.home

import dagger.Binds
import dagger.Module
import de.boettcher.storage.interactor.auth.GetUserTokenInteractor
import de.boettcher.storage.interactor.auth.IGetUserTokenInteractor
import de.boettcher.storage.interactor.barcode.ISendBarcodeInteractor
import de.boettcher.storage.interactor.barcode.SendBarcodeInteractor

@Module
abstract class HomeModule {

    @Binds
    abstract fun bindHomeNavigator(activity: HomeActivity): HomeNavigator

    @Binds
    abstract fun bindHomeStore(homeStore: HomeStore): IHomeStore

    @Binds
    abstract fun bindSendBarcodeInteractor(sendBarcodeInteractor: SendBarcodeInteractor): ISendBarcodeInteractor

    @Binds
    abstract fun bindGetUserTokenInteractor(getUserTokenInteractor: GetUserTokenInteractor): IGetUserTokenInteractor

}
