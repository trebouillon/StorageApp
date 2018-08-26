package de.boettcher.storage.scan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import dagger.android.support.DaggerAppCompatActivity
import de.boettcher.storage.R
import de.boettcher.storage.databinding.ActivityScanBinding
import de.boettcher.storage.utils.RequestCodeUtils
import kotlinx.android.synthetic.main.activity_scan.*
import javax.inject.Inject

class ScanActivity : DaggerAppCompatActivity(), IScanNavigator {

    companion object {

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, ScanActivity::class.java)
            activity.startActivityForResult(intent, RequestCodeUtils.REQUEST_CODE_SCAN)
        }

    }

    @Inject
    lateinit var viewModel: ScanViewModel
    private lateinit var binding: ActivityScanBinding
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan)
        binding.viewModel = this.viewModel

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }

        initBarcodeDetector()
    }

    private fun initCameraSource() {
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()
    }

    private fun initBarcodeDetector() {
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        barcodeDetector?.setProcessor(object : Detector.Processor<Barcode> {

            override fun release() {
                // nothing
            }

            override fun receiveDetections(detection: Detector.Detections<Barcode>?) {
                detection?.let {

                    if (it.detectedItems.size() == 1) {
                        viewModel.sendBarcodeData(it.detectedItems.valueAt(0).displayValue)
                    }

                }
            }

        })
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

    @SuppressLint("MissingPermission")
    override fun onSurfaceCreated() {
        cameraSource?.start(surfaceView.holder)
    }

    override fun onSurfaceDestroyed() {
        cameraSource?.stop()
    }

    override fun onStart() {
        super.onStart()
        initCameraSource()
    }

    override fun onStop() {
        cameraSource?.release()
        super.onStop()
    }

    override fun onDestroy() {
        barcodeDetector?.release()
        super.onDestroy()
    }
}
