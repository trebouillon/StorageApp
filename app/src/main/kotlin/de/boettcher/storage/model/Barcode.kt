package de.boettcher.storage.model

data class Barcode(val storageType: StorageType, val code : Long)

enum class StorageType {
  ITEM, STORE
}
