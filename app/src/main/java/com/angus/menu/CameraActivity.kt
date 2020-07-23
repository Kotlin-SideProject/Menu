package com.angus.menu

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : AppCompatActivity() {
    private val REQUEST_CAPTURE: Int = 500
    private var imageUri: Uri? = null
    private val REQUEST_CAMERA_PERMISSION: Int = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        if(ActivityCompat.checkSelfPermission(CameraActivity@this, CAMERA)
            == PackageManager.PERMISSION_DENIED ||
            ActivityCompat.checkSelfPermission(CameraActivity@this, WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(CameraActivity@this,
                arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA_PERMISSION)
        }else{
            openCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray)
    { if(requestCode == REQUEST_CAMERA_PERMISSION){
        if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            Log.d("CameraActivity.kt", "grantResults: camera ${grantResults[0]}, write external storage ${grantResults[1]}") // 0表示granted  -1 表示denied
            openCamera()
        }
        else{
            Toast.makeText(CameraActivity@this, "Permission denied", Toast.LENGTH_SHORT)
            Log.d("CameraActivity.kt", "grantResults: camera ${grantResults[0]}, write external storage ${grantResults[1]}") // 0表示granted  -1 表示denied
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun openCamera() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "My picture")
            put(MediaStore.Images.Media.DESCRIPTION, "Testing")
        }
         imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        Log.d("CameraActivity.kt", "imageUri:  ${imageUri}");
         camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(camera, REQUEST_CAPTURE)
//        imageUri?.apply {
//            setResult(Activity.RESULT_OK)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAPTURE){
            Log.d("CameraActivity.kt", "requestCode: ${requestCode}");
            if(resultCode == Activity.RESULT_OK){
                // Make sure the request was successful
                Log.d("CameraActivity.kt", "resultCode: ${resultCode}");
                imageView.setImageURI(imageUri)
            }
        }
    }
}