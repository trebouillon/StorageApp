package de.boettcher.storage.profile

import android.content.res.Resources
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import de.boettcher.storage.R
import de.boettcher.storage.ui.ToastDisplayer
import de.boettcher.storage.utils.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileStore: IProfileStore,
    private val resources: Resources,
    private val toaster: ToastDisplayer
) {

    val isLoggedIn = ObservableBoolean(false)
    val title = ObservableField<String>(TextUtils.EMPTY)
    val input = ObservableField<String>(TextUtils.EMPTY)

    fun onCreate() {
        profileStore.subscribe(Consumer(this::onStateChanged), AndroidSchedulers.mainThread())
        profileStore.initialize()
    }

    private fun onStateChanged(it: ProfileState) {
        when (it) {
            is ProfileState.LoggedIn -> setLoggedIn(it)
            is ProfileState.LoggedOut -> setLoggedOut()
            is ProfileState.Error -> toaster.showMessageLong(R.string.unknown_error)
        }
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
