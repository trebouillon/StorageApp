package de.boettcher.storage.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import de.boettcher.storage.R
import de.boettcher.storage.databinding.ActivityHomeBinding
import de.boettcher.storage.profile.ProfileActivity
import de.boettcher.storage.scan.ScanActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), HomeNavigator {

    private lateinit var binding: ActivityHomeBinding
    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = this.viewModel

        setSupportActionBar(toolbar)

        viewModel.onCreate()
    }

    override fun scanBarcode() {
        // todo check permissions upfront
        ScanActivity.startActivity(this)
    }

    override fun login() {
        ProfileActivity.startActivity(this)
    }
}