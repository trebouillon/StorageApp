package storage.boettcher.de.storageapp.api

interface IStorageClient {

  fun send(barcode: String): String

}
