package de.boettcher.storage.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.boettcher.storage.MainActivity

@Module
abstract class ActivityInjectionModule {

    @ContributesAndroidInjector
    internal abstract fun contributeSplashActivityInjector(): MainActivity

}
