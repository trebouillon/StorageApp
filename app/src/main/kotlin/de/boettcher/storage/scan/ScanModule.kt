package de.boettcher.storage.scan

import dagger.Binds
import dagger.Module

@Module
abstract class ScanModule {

    @Binds
    abstract fun provideScanNavigator(activity: ScanActivity): IScanNavigator

}
