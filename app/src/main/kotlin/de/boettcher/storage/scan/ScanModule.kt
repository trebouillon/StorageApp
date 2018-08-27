package de.boettcher.storage.scan

import dagger.Binds
import dagger.Module
import de.boettcher.storage.interactor.auth.GetUserTokenInteractor
import de.boettcher.storage.interactor.auth.IGetUserTokenInteractor
import de.boettcher.storage.interactor.barcode.ISendBarcodeInteractor
import de.boettcher.storage.interactor.barcode.SendBarcodeInteractor

@Module
abstract class ScanModule {

    @Binds
    abstract fun bindScanNavigator(activity: ScanActivity): IScanNavigator

    @Binds
    abstract fun bindScanStore(scanStore: ScanStore): IScanStore

    @Binds
    abstract fun bindSendBarcodeInteractor(sendBarcodeInteractor: SendBarcodeInteractor): ISendBarcodeInteractor

    @Binds
    abstract fun bindGetUserTokenInteractor(getUserTokenInteractor: GetUserTokenInteractor): IGetUserTokenInteractor

}
