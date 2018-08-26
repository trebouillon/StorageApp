package de.boettcher.storage.profile

import de.boettcher.storage.store.IState

sealed class ProfileState : IState {

    data class LoggedIn(val userId: String) : ProfileState()

    object LoggedOut : ProfileState()

}
