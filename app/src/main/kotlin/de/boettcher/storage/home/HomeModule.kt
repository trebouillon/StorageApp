package de.boettcher.storage.home

import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {

    @Binds
    abstract fun bindHomeNavigator(activity: HomeActivity): IHomeNavigator

}
