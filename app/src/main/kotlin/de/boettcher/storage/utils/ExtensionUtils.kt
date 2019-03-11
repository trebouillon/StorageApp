package de.boettcher.storage.utils

import de.boettcher.storage.model.StorageType
import java.util.regex.Pattern

fun String?.resolveStorageType(): StorageType = when {
    this == null -> StorageType.UNKNOWN
    BarcodeMatcher.ITEM_MATCHER.matcher(this).matches() -> StorageType.ITEM
    BarcodeMatcher.STORE_MATCHER.matcher(this).matches() -> StorageType.STORE
    else -> StorageType.UNKNOWN
}

object BarcodeMatcher {

    val ITEM_MATCHER: Pattern = Pattern.compile("[0,1]001 [\\d]{8} 0")
    val STORE_MATCHER: Pattern = Pattern.compile("2001 [\\d]{8} 0")

}
