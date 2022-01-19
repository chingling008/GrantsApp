package com.example.grantzapp.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.grantzapp.R
import com.example.grantzapp.databinding.ActivityFileReceiptBinding
import com.example.grantzapp.models.Amounts
import com.example.grantzapp.models.Receipt
import com.example.grantzapp.models.Students
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_file_receipt.*
import kotlinx.android.synthetic.main.activity_grants_award.*
import kotlinx.android.synthetic.main.results.*
import java.util.*
import kotlin.collections.ArrayList

class FileReceiptActivity : AppCompatActivity(), IFirebaseLoadDone {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    lateinit var database: DatabaseReference
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var date: String? = null
    private var datePickerDialog: DatePickerDialog? = null
    private lateinit var progressDialog: ProgressDialog
    lateinit var iFirebaseLoadDone: IFirebaseLoadDone
    private lateinit var spinner: Spinner
    private lateinit var binding: ActivityFileReceiptBinding
    private val CAMERA_REQUEST_CODE = 1
    var ImageUri: Uri? = null
    private var myUrl = ""
//    private var Purpose2 =""
//    private var dates2 =""
//    private var amount2 =""
//    private var admno2 =""


    lateinit var imageView: ImageView

    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseStore = FirebaseStorage.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        progressDialog = ProgressDialog(this)
        storageReference = FirebaseStorage.getInstance().reference
        database = FirebaseDatabase.getInstance().reference
        database = FirebaseDatabase.getInstance().getReference("StudentsTable")
        iFirebaseLoadDone = this

        spinner=findViewById(R.id.SearchableSpinner2)
        imageView = findViewById(R.id.imageView)



        database.addValueEventListener(object : ValueEventListener {
            var spinnerList: MutableList<Students> = ArrayList()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (spinnerSnapshot in snapshot.children)
                    spinnerList.add(spinnerSnapshot.getValue(Students::class.java)!!)
                iFirebaseLoadDone.onFirebaseLoadSuccess(spinnerList)
            }

            override fun onCancelled(error: DatabaseError) {
                iFirebaseLoadDone.onFirebaseFailed(error.message)
            }

        })

        val selectDate = findViewById<TextView>(R.id.txtPostedDates)
        date = intent.getStringExtra("Date").toString()
        selectDate!!.text = date
        selectDate!!.setOnClickListener(View.OnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            val month: Int = calendar.get(Calendar.MONTH)
            val year: Int = calendar.get(Calendar.YEAR)
            datePickerDialog = DatePickerDialog(
                this@FileReceiptActivity,
                { _, year, month, dayOfMonth ->
                    date = dayOfMonth.toString() + "-" + (month + 1) + "-" + year
                    Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
                    selectDate!!.text = date
                    onStart()
                }, day, month, year
            )
            datePickerDialog!!.updateDate(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
                    Calendar.DAY_OF_MONTH
                )
            )
            datePickerDialog!!.datePicker.minDate =
                System.currentTimeMillis() + 3 * 60 * 60 * 1000
            datePickerDialog!!.datePicker.maxDate =
                System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000
            datePickerDialog!!.show()
        })
//        binding.btnCamera2.setOnClickListener {
////            cameraCheckPermissions()
//        }
        binding.btnGallery.setOnClickListener {
            launchGallery()
        }
        binding.btnReceipt.setOnClickListener {
            val dates2: String = binding.txtPostedDates.text.toString()
            val purpose2: String = binding.txtPurpose2.text.toString()
            val amount2: String = binding.txtAmount2.text.toString()
            val admno2 = spinner.selectedItem.toString()

            Savenew(dates2, purpose2, amount2,admno2)
            if (dates2.isEmpty()) {
                Toast.makeText(
                    this@FileReceiptActivity,
                    "Please enter dates",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (purpose2.isEmpty()) {
                Toast.makeText(
                    this@FileReceiptActivity,
                    "Please enter purpose",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (amount2.isEmpty()) {
                Toast.makeText(
                    this@FileReceiptActivity,
                    "Please enter Amount paid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun uploadImage1() {
        when {
            ImageUri == null -> Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT)
                .show()
            else -> {
                progressDialog.setTitle(("please wait"))
                progressDialog.setMessage("uploading...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                val fileRef = storageReference!!.child(firebaseUser.uid)
                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(ImageUri!!)
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl

                }).addOnCompleteListener (
                    OnCompleteListener<Uri> { task ->
                        if (task.isSuccessful) {
                            val downloadUrl = task.result
                            myUrl = downloadUrl.toString()

                            val ref = FirebaseDatabase.getInstance().reference.child("StudentsTable")
                            val userMap = HashMap<String, Any>()
                            userMap["image2"] = myUrl
                            ref.child(firebaseUser.uid).updateChildren(userMap)

                            Toast.makeText(this, "uploaded successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, StudentsActivity::class.java)
                            startActivity(intent)
                            finish()
                            progressDialog.dismiss()

                        } else {
                            progressDialog.dismiss()
                        }
                    })
            }
        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    private fun Savenew(dates2: String, purpose2: String, amount2: String, admno2: String) {
        uploadImage1()
        val ref = FirebaseDatabase.getInstance().reference.child("StudentsTable")
        val userMap = HashMap<String, Any>()
        userMap["image2"] = myUrl
        userMap["purpose2"] = purpose2
        userMap["amount2"] = amount2
        userMap["dates2"] = dates2
        userMap["admno2"] = admno2
        ref.child(firebaseUser.uid).updateChildren(userMap)

    }
//    private fun cameraCheckPermissions() {
//        Dexter.withContext(this)
//            .withPermissions(
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                android.Manifest.permission.CAMERA
//            ).withListener(
//                object : MultiplePermissionsListener {
//                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//                        report?.let {
//                            if (report.areAllPermissionsGranted()) {
//                                camera()
//                            }
//                        }
//                    }
//                    override fun onPermissionRationaleShouldBeShown(
//                        p0: MutableList<PermissionRequest>?,
//                        p1: PermissionToken?
//                    ) {
//                        showRotationalDirectionForPermissions()
//                    }
//                }
//            ).onSameThread().check()
//    }
//    private fun showRotationalDirectionForPermissions() {
//        AlertDialog.Builder(this)
//            .setMessage("it looks like you have not turned on permissions")
//            .setPositiveButton("Go to setting") { _, _ ->
//                try {
//                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                    val uri = Uri.fromParts("packages", packageName, null)
//                    intent.data = uri
//                    startActivity(intent)
//
//                } catch (e: ActivityNotFoundException) {
//                    e.printStackTrace()
//                }
//            }
//
//            .setNegativeButton("Cancel"){ dialog, _->
//                dialog.dismiss()
//            }.show()
//    }
//    private fun camera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, CAMERA_REQUEST_CODE)
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            ImageUri = data?.data!!
            imageView.setImageURI(ImageUri)
        }
    }

    override fun onFirebaseLoadSuccess(spinnerList: MutableList<Students>) {
        val students_admno = getstudentsAdmno(spinnerList)
        val adapters = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,students_admno)
        spinner.adapter = adapters
    }
    private fun getstudentsAdmno(spinnerList: List<Students>): List<String> {
        val admno = ArrayList<String>()
        for (AdmNo in spinnerList)
            AdmNo.AdmNo?.let { admno.add(it) }
        return admno

    }

    override fun onFirebaseFailed(message: String) {

    }



}



