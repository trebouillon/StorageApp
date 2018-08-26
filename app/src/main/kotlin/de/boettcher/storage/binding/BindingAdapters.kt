package de.boettcher.storage.binding

import android.databinding.BindingAdapter
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import de.boettcher.storage.functional.Function

class BindingAdapters {

    @BindingAdapter("visibility")
    fun View.visible(visible: Boolean) {
        this.visibility = if (visible) View.VISIBLE else View.GONE
    }

    companion object {

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

    }
}