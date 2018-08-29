package de.boettcher.storage.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.android.support.DaggerAppCompatActivity
import de.boettcher.storage.R
import de.boettcher.storage.databinding.ActivityHomeBinding
import de.boettcher.storage.profile.ProfileActivity
import de.boettcher.storage.scan.ScanActivity
import de.boettcher.storage.utils.PermissionUtils
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), IHomeNavigator {

    @Inject
    lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rxPermissions: RxPermissions
    private var disposable = CompositeDisposable()
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = this.viewModel

        setSupportActionBar(toolbar)

        rxPermissions = RxPermissions(this)
    }

    override fun startTakeScan() {
        checkCameraPermission { ScanActivity.startTakeScan(this) }
    }

    override fun startPutScan() {
        checkCameraPermission { ScanActivity.startPutScan(this) }
    }

    override fun login() {
        ProfileActivity.startActivity(this)
    }

    private fun checkCameraPermission(onSuccess: () -> Unit) {
        disposable.add(
            rxPermissions.requestEach(android.Manifest.permission.CAMERA).subscribe {
                when {
                    it.granted -> onSuccess()
                    it.shouldShowRequestPermissionRationale -> {
                        showRationaleDialog { onSuccess() }
                    }
                    else -> {
                        showSettingsDialog()
                    }
                }
            })
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private fun showRationaleDialog(onSuccess: () -> Unit) {
        dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.permission_rationale_title)
            .setMessage(R.string.permission_rationale)
            .setPositiveButton(R.string.dialog_ok) { _, _ -> checkCameraPermission(onSuccess) }
            .create()
        dialog?.show()
    }

    private fun showSettingsDialog() {
        dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.permission_rationale_title)
            .setMessage(R.string.permission_settings_message)
            .setPositiveButton(R.string.dialog_ok) { _, _ -> dialog?.dismiss() }
            .setNegativeButton(R.string.dialog_settings) { _, _ ->
                let {
                    it.dialog?.dismiss()
                    PermissionUtils.showPermissionSettings(this)
                }
            }
            .create()
        dialog?.show()
    }
}
