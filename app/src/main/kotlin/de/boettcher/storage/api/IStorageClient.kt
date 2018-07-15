package de.boettcher.storage.api

interface IStorageClient {

  fun send(barcode: String): String

}
