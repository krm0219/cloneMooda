package com.krm0219.mooda.test

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.krm0219.mooda.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraAPI(context: Context) {
    companion object {
        private const val TAG = "CameraAPI"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private var imageCapture: ImageCapture? = null

    private var outputDirectory: File
    private var cameraExecutor: ExecutorService


    init {

        outputDirectory = getOutputDirectory(context)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun getOutputDirectory(context: Context): File {

        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }


    fun startCamera(context: Context, viewFinder: PreviewView) {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {

                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    context as LifecycleOwner, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e("CameraActivity.TAG", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }

    fun capture(activity: Activity) {


    }

    var resizeBitmap: Bitmap? = null

    fun takePhoto(context: Context, imageView: ImageView) {

        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, msg)

                    var bitmap1: Bitmap? = null

                    if (Build.VERSION.SDK_INT >= 29) {

                        val source: ImageDecoder.Source = ImageDecoder.createSource(context.contentResolver, savedUri)

                        try {

                            bitmap1 = ImageDecoder.decodeBitmap(source) { decoder: ImageDecoder, _: ImageDecoder.ImageInfo, _: ImageDecoder.Source ->

                                decoder.isMutableRequired = true
                                decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                            }
                        } catch (e: java.lang.Exception) {

                        }
                    } else {

                        bitmap1 = MediaStore.Images.Media.getBitmap(context.contentResolver, savedUri)
                    }

                    resizeBitmap = getResizeBitmap(bitmap1)

                    imageView.setImageBitmap(resizeBitmap)
                    imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    //  imageView.setImageURI(savedUri)
                }
            })
    }


    fun getResizeBitmap(originalBitmap: Bitmap?): Bitmap? {
        var rotatebmp: Bitmap? = null
        if (originalBitmap == null) {
            return null
        }
        try {
            var height = originalBitmap.height
            var width = originalBitmap.width
            Log.e(TAG, "★★★★★   first  :: width - $width ,height - $height")
            if (height > 900) {
                val ratio: Int
                if (width > height) {
                    ratio = width / 1000
                    width = 1000
                    height = height / ratio
                } else if (height > width) {
                    ratio = height / 1000
                    height = 1000
                    width = width / ratio
                } else {
                    height = 1000
                    width = 1000
                }
                Log.e(TAG, "★★★★★    :: width - $width ,height - $height")
                if (0 < height) {
                    val resized = Bitmap.createScaledBitmap(originalBitmap, width, height, false)
                    val matrix = Matrix()
                    matrix.preRotate(90f)
                    rotatebmp = Bitmap.createBitmap(resized, 0, 0, resized.width, resized.height, matrix, false)
                }
            } else {
                rotatebmp = originalBitmap
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "getResizeBitmap  Exception : $e")
            e.printStackTrace()
        }
        return rotatebmp
    }


    fun shutdown() {

        cameraExecutor.shutdown()
    }
}