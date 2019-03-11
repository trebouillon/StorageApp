package de.boettcher.storage.persistence

import android.content.Context
import javax.inject.Inject

interface IPersistenceService {

    fun get(key: String): String?

    fun put(key: String, value: String)

    fun remove(key: String)

}

class PersistenceService @Inject constructor(context: Context) : IPersistenceService {

    // do not change name in order to keep login data
    private val preferences = context.getSharedPreferences("account", Context.MODE_PRIVATE)

    override fun get(key: String): String? = preferences.getString(key, null)

    override fun put(key: String, value: String) = preferences.edit().putString(key, value).apply()

    override fun remove(key: String) = preferences.edit().remove(key).apply()
}
