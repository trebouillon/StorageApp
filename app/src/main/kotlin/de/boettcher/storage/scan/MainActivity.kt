package de.boettcher.storage.scan

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import dagger.android.support.DaggerAppCompatActivity
import de.boettcher.storage.R
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    private var dialog: AlertDialog? = null
    //    @Inject
//    lateinit var scanViewModel: ScanViewModel
    private lateinit var viewStateDisposable: Disposable

    // TODO("quick'n'dirty" -> DELETE ^^)
    enum class Type {
        TAKE, PUT, NONE
    }

    var type = Type.NONE
    private var putItemFirst: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        take.setOnClickListener {
//            type = Type.TAKE
//            scan()
//        }
//
//        put.setOnClickListener {
//            type = Type.PUT
//            scan()
//        }
//
//        login.setOnClickListener {
//            login()
//        }
//
//        extra.setOnClickListener {
//            Toast.makeText(this, R.string.action_menu, Toast.LENGTH_LONG).show()
//        }
//
//        viewStateDisposable = scanViewModel.observe().subscribe(
//            { onScanViewStateChange(it) },
//            { Toast.makeText(this, it.message, Toast.LENGTH_LONG).show() }
//        )
    }

    private fun onScanViewStateChange(scanViewState: ScanViewState) {
        when (scanViewState) {
            is ScanViewState.Success -> showToastMessage(scanViewState.result)
            is ScanViewState.Error -> showToastMessage(scanViewState.message)
//            is ScanViewState.NoToken -> {
//                showToastMessage(getString(R.string.no_user_token))
//                scanViewModel.resetState()
//            }
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
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

    override fun onDestroy() {
        dialog?.let {
            dialog = null
        }

        if (!viewStateDisposable.isDisposed) viewStateDisposable.dispose()

        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

//        scanResult?.let {
//
//            scanResult.contents?.let {
//                when (type) {
//                    Type.TAKE -> {
//                        scanViewModel.sendBarcode(scanResult.contents)
//                        type = Type.NONE
//                    }
//                    Type.PUT -> {
//                        if (putItemFirst == null) {
//                            putItemFirst = scanResult.contents
//                            scan()
//                        } else {
//                            scanViewModel.sendBarcode(putItemFirst!!, scanResult.contents)
//                            putItemFirst = null
//                            type = Type.NONE
//                        }
//                    }
//                    else -> {
//                    }
//                }
//            }
//        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    // TODO("will be removed from here later")
    private fun scan() {
        val integrator = IntentIntegrator(this)
        integrator.setTitleByID(R.string.barcode_install_prompt_title)
        dialog = integrator.initiateScan(android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK)
    }

//    @SuppressLint("InflateParams")
//    private fun login() {
//        val content = LayoutInflater.from(this).inflate(R.layout.dialog_login, null)
//
//        val builder = AlertDialog.Builder(this).setView(content)
//            .setPositiveButton(
//                R.string.dialog_login_ok
//            ) { _, _ ->
//                scanViewModel.saveUserToken(content.findViewById<EditText>(R.id.user_token_input).text.toString())
//                dialog?.let {
//                    dialog!!.dismiss()
//                }
//            }
//
//
//        dialog = builder.show()
//    }
}
