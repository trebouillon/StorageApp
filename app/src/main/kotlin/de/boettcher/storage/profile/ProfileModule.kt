package de.boettcher.storage.profile

import dagger.Binds
import dagger.Module
import de.boettcher.storage.interactor.auth.*

@Module
abstract class ProfileModule {

    @Binds
    abstract fun bindProfileStore(profileStore: ProfileStore): IProfileStore

    @Binds
    abstract fun bindSaveUserTokenInteractor(saveUserTokenInteractor: SaveUserTokenInteractor): ISaveUserTokenInteractor

    @Binds
    abstract fun bindRemoveUserTokenInteractor(removeUserTokenInteractor: RemoveUserTokenInteractor): IRemoveUserTokenInteractor

    @Binds
    abstract fun bindGetUserTokenInteractor(getUserTokenInteractor: GetUserTokenInteractor): IGetUserTokenInteractor

}
