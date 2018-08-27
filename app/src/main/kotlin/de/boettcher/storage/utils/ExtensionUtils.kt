package de.boettcher.storage.utils

import de.boettcher.storage.model.StorageType

fun String.resolveStorageType(): StorageType {
    val indicator = substring(0..1).toInt()

    return when (indicator) {
        in 0..1 -> StorageType.ITEM
        2 -> StorageType.STORE
        else -> StorageType.ITEM
    }
}

