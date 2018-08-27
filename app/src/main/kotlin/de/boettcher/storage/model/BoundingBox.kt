package de.boettcher.storage.model

import android.graphics.RectF
import com.google.android.gms.common.images.Size
import com.google.android.gms.vision.barcode.Barcode
import de.boettcher.storage.scan.ScanUtils

data class BoundingBox(val size: Size?, val barcode: Barcode) {

    fun getHeight(): Int {
        if (size == null) return ScanUtils.PREVIEW_WIDTH

        return Math.max(size.height, size.width)
    }

    fun getWidth(): Int {
        if (size == null) return ScanUtils.PREVIEW_HEIGHT

        return Math.min(size.height, size.width)
    }

    fun getRectF(): RectF {
        return RectF(barcode.boundingBox)
    }

    fun getValue(): String {
        return barcode.displayValue
    }

}
