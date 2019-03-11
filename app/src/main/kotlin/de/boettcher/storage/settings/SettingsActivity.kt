package de.boettcher.storage.settings

import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import de.boettcher.storage.R
import de.boettcher.storage.databinding.ActivitySettingsBinding
import javax.inject.Inject

class SettingsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivitySettingsBinding>(this, R.layout.activity_settings)
            .also {
                it.viewModel = viewModel
            }
    }

}
