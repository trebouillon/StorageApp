package de.boettcher.storage.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.boettcher.storage.home.HomeActivity
import de.boettcher.storage.home.HomeModule
import de.boettcher.storage.profile.ProfileActivity
import de.boettcher.storage.profile.ProfileModule
import de.boettcher.storage.scan.ScanActivity
import de.boettcher.storage.scan.ScanModule
import de.boettcher.storage.settings.SettingsActivity
import de.boettcher.storage.settings.SettingsModule

@Module
abstract class ActivityInjectionModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivityInjector(): HomeActivity

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileActivityInjector(): ProfileActivity

    @ContributesAndroidInjector(modules = [ScanModule::class])
    abstract fun contributeScanActivityInjector(): ScanActivity

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun contributeSettingsActivityInjector(): SettingsActivity

}
