package de.boettcher.storage.model

data class Barcode(val storageType: StorageType, val code : String)

enum class StorageType {
  ITEM, STORE
}
