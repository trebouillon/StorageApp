package de.boettcher.storage.home

import android.app.AlertDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import dagger.android.support.DaggerAppCompatActivity
import de.boettcher.storage.R
import de.boettcher.storage.databinding.ActivityHomeBinding
import de.boettcher.storage.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), HomeNavigator {

    private lateinit var binding: ActivityHomeBinding
    private var dialog: AlertDialog? = null
    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = this.viewModel
        viewModel.onCreate()
    }

    override fun scanBarcode() {
        val integrator = IntentIntegrator(this)
        integrator.setTitleByID(R.string.barcode_install_prompt_title)
        dialog = integrator.initiateScan(android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK)
    }

    override fun login() {
        ProfileActivity.startActivity(this)
    }

    override fun onPause() {
        dialog?.let {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
                dialog = null
            }
        }

        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        scanResult?.let {
            viewModel.sendBarcode(scanResult.contents)
        }
    }
}
