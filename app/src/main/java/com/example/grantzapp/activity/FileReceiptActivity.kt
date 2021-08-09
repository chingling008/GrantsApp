package com.example.grantzapp.activity

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.example.grantzapp.R
import com.example.grantzapp.databinding.ActivityFileReceiptBinding

import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class FileReceiptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFileReceiptBinding
    private val CAMERA_REQUEST_CODE =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera2.setOnClickListener {
            cameraCheckPermissions()
        }

        binding.btnGallery.setOnClickListener {

        }
    }



    private fun cameraCheckPermissions() {
        Dexter.withContext(this)
            .withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA).withListener(
                object :MultiplePermissionsListener{
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()){
                                camera()
                            }
                        }

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                      showRotationalDirectionForPermissions()
                    }

                }
            ).onSameThread().check()
    }

    private fun showRotationalDirectionForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("it looks like you havent turned on permissions")
            .setPositiveButton("Go to setting"){_,_ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("packages", packageName, null)
                    intent.data = uri
                    startActivity(intent)

                }catch (e:ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel"){dialog, _->
                dialog.dismiss()
            }.show()

    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity. RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(imageBitmap)
        }
    }

}
