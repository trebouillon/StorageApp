package de.boettcher.storage.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Binds
import dagger.Module
import dagger.Provides
import de.boettcher.storage.api.IStorageClient
import de.boettcher.storage.api.StorageClient
import de.boettcher.storage.repository.AccountRepository
import de.boettcher.storage.repository.IAccountRepository
import de.boettcher.storage.repository.IScanRepository
import de.boettcher.storage.repository.ScanRepository
import javax.inject.Singleton

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Singleton
    @Binds
    abstract fun bindStorageClient(storageClient: StorageClient): IStorageClient

    @Singleton
    @Binds
    abstract fun bindBarcodeRepository(barcodeRepository: ScanRepository): IScanRepository

    @Singleton
    @Binds
    abstract fun bindAccountRepository(accountRepository: AccountRepository): IAccountRepository

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun provideResources(context: Context): Resources {
            return context.resources
        }

    }

}
