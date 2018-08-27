package de.boettcher.storage.binding

import android.databinding.BindingAdapter
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import de.boettcher.storage.functional.Function
import de.boettcher.storage.model.BoundingBox
import de.boettcher.storage.ui.view.BarcodeBoxView

class BindingAdapters {

    companion object {

        @JvmStatic
        @BindingAdapter("visibility")
        fun View.visible(visible: Boolean) {
            this.visibility = if (visible) View.VISIBLE else View.GONE
        }

        @BindingAdapter("createSurface", "destroySurface")
        @JvmStatic
        fun SurfaceView.addCallback(createSurface: Function, destroySurface: Function) {
            holder.addCallback(object : SurfaceHolder.Callback {

                override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
                    // ignore
                }

                override fun surfaceDestroyed(p0: SurfaceHolder?) {
                    destroySurface.apply()
                }

                override fun surfaceCreated(p0: SurfaceHolder?) {
                    createSurface.apply()
                }

            })
        }

        @BindingAdapter("boundingBox")
        @JvmStatic
        fun BarcodeBoxView.show(boundingBox: BoundingBox?) {
            visibility = View.GONE

            boundingBox?.let {
                setBoundingBox(it)
                postInvalidate()
                visibility = View.VISIBLE
            }
        }

    }
}
