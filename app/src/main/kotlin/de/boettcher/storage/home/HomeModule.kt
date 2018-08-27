package de.boettcher.storage.home

import dagger.Binds
import dagger.Module
import de.boettcher.storage.interactor.auth.GetUserTokenInteractor
import de.boettcher.storage.interactor.auth.IGetUserTokenInteractor

@Module
abstract class HomeModule {

    @Binds
    abstract fun bindHomeNavigator(activity: HomeActivity): IHomeNavigator

    @Binds
    abstract fun bindHomeStore(homeStore: HomeStore): IHomeStore

    @Binds
    abstract fun bindGetUserTokenInteractor(getUserTokenInteractor: GetUserTokenInteractor): IGetUserTokenInteractor

}
