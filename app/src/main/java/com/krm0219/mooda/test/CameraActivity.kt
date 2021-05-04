package com.krm0219.mooda.test

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.PixelCopy
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.krm0219.mooda.R
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.FileOutputStream
import java.lang.Exception


// Jetpack CameraX
class CameraActivity : AppCompatActivity() {

    lateinit var cameraAPI: CameraAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        cameraAPI = CameraAPI(this)

        // Request camera permissions
        if (allPermissionsGranted()) {

            cameraAPI.startCamera(this, viewFinder)
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Set up the listener for take photo button
        camera_capture_button.setOnClickListener {

            cameraAPI.takePhoto(this, imageView)
        }

        camera_saved_button.setOnClickListener {

            imageView.buildDrawingCache()
            val view = imageView.drawingCache

            try {

                val fos = FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/abc.png")
                view.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {

                Log.e("Exception", "Error $e")
            }




        //    cameraAPI.capture()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraAPI.shutdown()

    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }


    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {

                cameraAPI.startCamera(this@CameraActivity, viewFinder)
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}