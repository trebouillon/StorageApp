package de.boettcher.storage.ui

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

class ToastDisplayer(private val context: Context) {

    fun showMessageShort(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessageShort(@StringRes message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessageLong(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showMessageLong(@StringRes message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}
