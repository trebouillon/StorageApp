package de.boettcher.storage.settings

import de.boettcher.storage.interactor.settings.Endpoint
import de.boettcher.storage.store.IState

sealed class SettingsState : IState {

    object Idle : SettingsState()

    data class Default(val endpoint: Endpoint) : SettingsState()

    object Success : SettingsState()

    object Error : SettingsState()

}
