package de.boettcher.storage.repository

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AccountRepository @Inject constructor(context: Context) : IAccountRepository {

    companion object {
        private const val KEY_TOKEN = "userToken"
    }

    private val preferences = context.getSharedPreferences("account", Context.MODE_PRIVATE)

    override fun getUserToken(): Maybe<String> {
        return Maybe.create<String> {
            val userToken = preferences.getString(KEY_TOKEN, null)
            when (userToken) {
                null -> it.onComplete()
                else -> it.onSuccess(userToken)
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun saveUserToken(token: String): Completable {
        return Completable.fromRunnable {
            val editor = preferences.edit()
            editor.putString(KEY_TOKEN, token).apply()
        }.subscribeOn(Schedulers.io())
    }

    override fun removeUserToken(): Completable {
        return Completable.fromRunnable {
            val editor = preferences.edit()
            editor.remove(KEY_TOKEN).apply()
        }.subscribeOn(Schedulers.io())
    }
}
