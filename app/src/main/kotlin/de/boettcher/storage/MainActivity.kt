package de.boettcher.storage

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import de.boettcher.storage.api.StorageClient
import java.lang.ref.WeakReference

class MainActivity : DaggerAppCompatActivity() {

  private var dialog: AlertDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    take.setOnClickListener { scan() }

    // TODO("implement")
    put.setOnClickListener { Toast.makeText(this,
      R.string.action_put, Toast.LENGTH_LONG).show() }

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
      SendTask(this).execute(scanResult.contents)
    }

    super.onActivityResult(requestCode, resultCode, data)
  }

  private fun scan() {
    val integrator = IntentIntegrator(this)
    integrator.setTitleByID(R.string.barcode_install_prompt_title)
    dialog = integrator.initiateScan(android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK)
  }

  class SendTask(context: Context) : AsyncTask<String, Void, String>() {

    private val weakContext = WeakReference(context)

    override fun doInBackground(vararg args: String?): String? {
      return StorageClient().send(args[0]!!)
    }

    override fun onPostExecute(result: String?) {
      weakContext.get()?.let {
        Toast.makeText(it, result, Toast.LENGTH_LONG).show()
      }
    }
  }
}
