package de.boettcher.storage.profile

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import de.boettcher.storage.R
import de.boettcher.storage.databinding.ActivityProfileBinding
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class ProfileActivity : DaggerAppCompatActivity() {

    companion object {

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, ProfileActivity::class.java)
            activity.startActivity(intent)
        }

    }

    @Inject
    lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = this.viewModel

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.onCreate()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            if (it.itemId == android.R.id.home) {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
