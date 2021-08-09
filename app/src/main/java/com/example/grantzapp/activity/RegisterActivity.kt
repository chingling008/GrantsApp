package com.example.grantzapp.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grantzapp.R
import com.example.grantzapp.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_student_register.*
import kotlinx.android.synthetic.main.results.*
import kotlinx.android.synthetic.main.student_details.*
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {


    private lateinit var firebaseUser: FirebaseUser
    lateinit var ImageUri: Uri
    private var myUrl = ""
    private lateinit var imageView: ImageView
    private val PICK_IMAGE_REQUEST = 71
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null


    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)



        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference.child("Users")


        imageView = findViewById(R.id.ntdImg)

        imageView.setOnClickListener {
            launchGallery()
        }




        binding.buttonRegister.setOnClickListener {
            val firstName: String = binding.txtFirstName.text.toString()
            val lastName: String = binding.txtLastName.text.toString()
            val email: String = binding.txtEmail.text.toString().trim()
//            val password: String = binding.txtPassword.text.toString()
//            val cPassword: String = binding.txtCpassword.text.toString()


            if (firstName.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Please enter firstName", Toast.LENGTH_SHORT)
                    .show()
            } else if (lastName.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Please enter lastname", Toast.LENGTH_SHORT)
                    .show()
            } else if (email.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Please enter email", Toast.LENGTH_SHORT)
                    .show()
//            } else if (password.isEmpty()) {
//                Toast.makeText(this@RegisterActivity, "Please enter password", Toast.LENGTH_SHORT)
//                    .show()
//            } else if (cPassword.isEmpty()) {
//                Toast.makeText(
//                    this@RegisterActivity,
//                    "Please confirm password ",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else if (!password.equals(cPassword)) {
//                Toast.makeText(this@RegisterActivity, "password mismatched", Toast.LENGTH_SHORT)
//                    .show()
            } else {
                progressDialog.setTitle(("please wait"))
                progressDialog.setMessage("creating an account...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                uploadImage()

            }
        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            ImageUri = data?.data!!
            imageView.setImageURI(ImageUri)
        }
    }


    private fun uploadImage() {
        when {
            ImageUri == null -> Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT)
                .show()

            else -> {
                progressDialog.setTitle(("please wait"))
                progressDialog.setMessage("uploading...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()


                val fileRef = storageReference!!.child(firebaseUser!!.uid)
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

                }).addOnCompleteListener(
                    OnCompleteListener<Uri> { task ->
                        if (task.isSuccessful) {
                            val downloadUrl = task.result
                            myUrl = downloadUrl.toString()
                            val firstName: String = binding.txtFirstName.text.toString()
                            val lastName: String = binding.txtLastName.text.toString()
                            val email: String = binding.txtEmail.text.toString().trim()
                            saveDetails(firstName,lastName,email,myUrl)


                        } else {
                            progressDialog.dismiss()
                        }
                    })
            }
        }
    }

    fun saveDetails(firstName: String, lastName: String, email: String,myurl: String) {
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val CurrentUserId = userRef.push().key.toString()
        val userMap = HashMap<String, Any>()
        userMap["uid"] = CurrentUserId
        userMap["FirstName"] = firstName
        userMap["lastName"] = lastName
        userMap["Email"] = email
        userMap["Image"] = myurl


        userRef.child(CurrentUserId).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {

            }
    }
}






