package de.boettcher.storage.settings

import dagger.Binds
import dagger.Module
import dagger.Provides
import de.boettcher.storage.interactor.settings.IUpdateEndpointInteractor
import de.boettcher.storage.interactor.settings.UpdateEndpointInteractor
import de.boettcher.storage.ui.ToastDisplayer

@Module
abstract class SettingsModule {

    @Binds
    abstract fun bindSettingsStore(settingsStore: SettingsStore): ISettingsStore

    @Binds
    abstract fun bindUpdateEndpointInteractor(updateEndpointInteractor: UpdateEndpointInteractor): IUpdateEndpointInteractor

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideSettingsViewModel(
            settingsStore: ISettingsStore,
            toastDisplayer: ToastDisplayer
        ): SettingsViewModel = SettingsViewModel(settingsStore, toastDisplayer)

    }

}
