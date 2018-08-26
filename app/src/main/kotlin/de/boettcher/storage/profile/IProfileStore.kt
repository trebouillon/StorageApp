package de.boettcher.storage.profile

import de.boettcher.storage.store.IStore

interface IProfileStore : IStore<ProfileState> {

    fun initialize()

    fun login(userId: String)

    fun logout()

}
