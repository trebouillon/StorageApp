package de.boettcher.storage.profile

import android.content.res.Resources
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import de.boettcher.storage.R
import de.boettcher.storage.utils.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileStore: IProfileStore,
    private val resources: Resources
) {

    val isLoggedIn = ObservableBoolean(false)
    val title = ObservableField<String>(TextUtils.EMPTY)
    val input = ObservableField<String>(TextUtils.EMPTY)

    fun onCreate() {
        profileStore.subscribe(Consumer {
            when (it) {
                is ProfileState.LoggedIn -> setLoggedIn(it)
                is ProfileState.LoggedOut -> setLoggedOut()
            }
        }, AndroidSchedulers.mainThread())
        profileStore.initialize()
    }

    fun login() {
        profileStore.login(input.get()!!)
    }

    fun logout() {
        profileStore.logout()
    }

    private fun setLoggedIn(state: ProfileState.LoggedIn) {
        isLoggedIn.set(true)
        title.set(resources.getString(R.string.profile_logged_in, state.userId))
    }

    private fun setLoggedOut() {
        isLoggedIn.set(false)
        title.set(resources.getString(R.string.profile_logged_out))
        input.set(TextUtils.EMPTY)
    }

}
