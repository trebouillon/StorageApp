package de.boettcher.storage.binding

import android.databinding.BindingAdapter
import android.view.View

@BindingAdapter("visibility")
fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
