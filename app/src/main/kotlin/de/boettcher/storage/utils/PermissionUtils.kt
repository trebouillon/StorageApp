package de.boettcher.storage.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class PermissionUtils {

    companion object {

        private const val PACKAGE = "package:"

        @JvmStatic
        fun showPermissionSettings(context: Context?) {
            context?.let {

                val settingsIntent = Intent()
                settingsIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                settingsIntent.addCategory(Intent.CATEGORY_DEFAULT)
                settingsIntent.data = Uri.parse(PACKAGE + it.packageName)
                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                it.startActivity(settingsIntent)

            }
        }

    }

}
