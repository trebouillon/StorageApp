package de.boettcher.storage.api

interface IStorageClient {

  fun send(payload: String): String

}
