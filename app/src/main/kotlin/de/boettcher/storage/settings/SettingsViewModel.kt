package de.boettcher.storage.settings

import android.databinding.ObservableField
import de.boettcher.storage.ui.ToastDisplayer
import de.boettcher.storage.utils.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class SettingsViewModel(
    private val settingsStore: ISettingsStore,
    private val toastDisplayer: ToastDisplayer
) {

    val endpoint = ObservableField<String>(TextUtils.EMPTY)
    private val disposable = CompositeDisposable()

    fun start() {
        val stateDisposable = settingsStore.subscribe(
            Consumer(this::onStateChange),
            AndroidSchedulers.mainThread()
        )
        disposable.add(stateDisposable)
        settingsStore.init()
    }

    private fun onStateChange(settingsState: SettingsState) {
        when (settingsState) {
            is SettingsState.Success -> toastDisplayer.showMessageShort("endoint updated successfully")
            is SettingsState.Error -> toastDisplayer.showMessageLong("endpoint could not be updated")
            is SettingsState.Default -> endpoint.set(settingsState.endpoint)
        }
    }

    fun onUpdateEndpoint() = settingsStore.updateEndpoint(endpoint.get() ?: TextUtils.EMPTY)

    fun stop() {
        disposable.dispose()
    }

}
