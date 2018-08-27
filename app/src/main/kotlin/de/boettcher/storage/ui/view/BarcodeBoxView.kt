package de.boettcher.storage.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import de.boettcher.storage.model.BoundingBox

class BarcodeBoxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val textPaint = Paint()
    private var rectF: RectF? = null
    private var boundingBox: BoundingBox? = null

    init {
        paint.color = Color.CYAN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f

        textPaint.color = Color.CYAN
        textPaint.textSize = 36f
    }

    fun setBoundingBox(box: BoundingBox) {
        boundingBox = box
        rectF = box.getRectF()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {

            boundingBox?.let { box ->

                val heightFactor = height.toFloat() / box.getHeight()
                val widthFactor = width.toFloat() / box.getWidth()

                rectF?.let { rect ->

                    it.drawRect(
                        scaleX(rect.left, widthFactor),
                        scaleY(rect.top, heightFactor),
                        scaleX(rect.right, widthFactor),
                        scaleY(rect.bottom, heightFactor),
                        paint
                    )

                    it.drawText(
                        box.getValue(),
                        scaleX(rect.left, widthFactor),
                        scaleY(rect.bottom, heightFactor),
                        textPaint
                    )

                }

            }

        }
    }

    fun scaleX(horizontal: Float, factor: Float): Float {
        return horizontal * factor
    }

    fun scaleY(vertical: Float, factor: Float): Float {
        return vertical * factor
    }

}
