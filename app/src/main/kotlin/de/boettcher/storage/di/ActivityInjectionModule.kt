package de.boettcher.storage.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.boettcher.storage.home.HomeActivity
import de.boettcher.storage.home.HomeModule
import de.boettcher.storage.profile.ProfileActivity
import de.boettcher.storage.profile.ProfileModule

@Module
abstract class ActivityInjectionModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivityInjector(): HomeActivity

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileActivityInjector(): ProfileActivity

}
