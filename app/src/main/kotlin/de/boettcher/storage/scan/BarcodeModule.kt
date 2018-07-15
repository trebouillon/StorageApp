package de.boettcher.storage.scan

import dagger.Module
import dagger.Provides
import de.boettcher.storage.api.IStorageClient
import de.boettcher.storage.api.StorageClient
import de.boettcher.storage.interactor.ISendBarcodeInteractor
import de.boettcher.storage.interactor.SendBarcodeInteractor
import de.boettcher.storage.repository.BarcodeRepository
import de.boettcher.storage.repository.IBarcodeRepository
import javax.inject.Singleton

@Module
class BarcodeModule {

    @Singleton
    @Provides
    internal fun provideScanViewModel(sendBarcodeInteractor: ISendBarcodeInteractor): ScanViewModel {
        return ScanViewModel(sendBarcodeInteractor)
    }

    @Singleton
    @Provides
    internal fun provideStorageClient(): IStorageClient {
        return StorageClient()
    }

    @Singleton
    @Provides
    internal fun provideBarcodeRepository(storageClient: IStorageClient): IBarcodeRepository {
        return BarcodeRepository(storageClient)
    }

    @Singleton
    @Provides
    internal fun provideSendBarcodeInteractor(barcodeRepository: IBarcodeRepository): ISendBarcodeInteractor {
        return SendBarcodeInteractor(barcodeRepository)
    }

}
