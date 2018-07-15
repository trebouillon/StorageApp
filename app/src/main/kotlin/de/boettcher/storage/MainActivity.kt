package de.boettcher.storage

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import dagger.android.support.DaggerAppCompatActivity
import de.boettcher.storage.interactor.SendBarcodeInteractor
import de.boettcher.storage.model.Barcode
import de.boettcher.storage.model.BarcodeData
import de.boettcher.storage.model.ScanType
import de.boettcher.storage.model.StorageType
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private var dialog: AlertDialog? = null
    @Inject
    lateinit var sendBarcodeInteractor: SendBarcodeInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        take.setOnClickListener { scan() }

        // TODO("implement")
        put.setOnClickListener {
            Toast.makeText(
                this,
                R.string.action_put, Toast.LENGTH_LONG
            ).show()
        }

        login.setOnClickListener {
            Toast.makeText(this, R.string.action_login, Toast.LENGTH_LONG).show()
        }

        extra.setOnClickListener {
            Toast.makeText(this, R.string.action_menu, Toast.LENGTH_LONG).show()
        }
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

        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        scanResult?.let {
            val barcode = BarcodeData(
                scanType = ScanType.TAKE,
                userId = "tre",
                barcodes = listOf(
                    Barcode(
                        storageType = StorageType.ITEM,
                        code = scanResult.contents.toLong()
                    )
                )
            )

            sendBarcodeInteractor.send(barcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess(), onError())
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onError(): (Throwable) -> Unit =
        { Toast.makeText(this, it.message, Toast.LENGTH_LONG).show() }

    private fun onSuccess(): (String) -> Unit =
        { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }

    private fun scan() {
        val integrator = IntentIntegrator(this)
        integrator.setTitleByID(R.string.barcode_install_prompt_title)
        dialog = integrator.initiateScan(android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK)
    }
}
