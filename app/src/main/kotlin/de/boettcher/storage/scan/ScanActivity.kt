package de.boettcher.storage.scan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.SparseArray
import android.view.MenuItem
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import dagger.android.support.DaggerAppCompatActivity
import de.boettcher.storage.R
import de.boettcher.storage.databinding.ActivityScanBinding
import de.boettcher.storage.model.BoundingBox
import kotlinx.android.synthetic.main.activity_scan.*
import javax.inject.Inject

class ScanActivity : DaggerAppCompatActivity(), IScanNavigator {

    companion object {

        private const val EXTRA_SCAN_TYPE = "extra.scan.type"
        private const val EXTRA_SCAN_TYPE_TAKE = "extra.scan.type.take"
        private const val EXTRA_SCAN_TYPE_PUT = "extra.scan.type.put"

        fun startTakeScan(activity: Activity) {
            val intent = Intent(activity, ScanActivity::class.java)
            intent.putExtra(EXTRA_SCAN_TYPE, EXTRA_SCAN_TYPE_TAKE)
            activity.startActivity(intent)
        }

        fun startPutScan(activity: Activity) {
            val intent = Intent(activity, ScanActivity::class.java)
            intent.putExtra(EXTRA_SCAN_TYPE, EXTRA_SCAN_TYPE_PUT)
            activity.startActivity(intent)
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

        when (intent.getStringExtra(EXTRA_SCAN_TYPE)) {
            EXTRA_SCAN_TYPE_TAKE -> viewModel.startTake()
            EXTRA_SCAN_TYPE_PUT -> viewModel.startPut()
        }

        initBarcodeDetector()
    }

    private fun initCameraSource() {
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(ScanUtils.PREVIEW_WIDTH, ScanUtils.PREVIEW_HEIGHT)
            .setRequestedFps(15.0f)
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
                    detectAndSendBarcode(it)
                }
            }

            fun detectAndSendBarcode(detections: Detector.Detections<Barcode>): SparseArray<Barcode>? {
                return detections.detectedItems.takeIf {
                    it.size() == 1
                }?.apply {
                    viewModel.displayBoundingBox(
                        boundingBox = BoundingBox(
                            size = cameraSource?.previewSize,
                            barcode = valueAt(0)
                        )
                    )
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

    override fun close() {
        finish()
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
        viewModel.onDestroy()
        barcodeDetector?.release()
        super.onDestroy()
    }
}
