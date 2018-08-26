package de.boettcher.storage.home

import de.boettcher.storage.store.IStore

interface IHomeStore : IStore<HomeState> {

    fun sendBarcode(value: String)

}
