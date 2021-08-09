package com.example.grantzapp.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.grantzapp.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.results.*
import kotlinx.android.synthetic.main.student_details.*
import java.util.*

class CorrectionActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var progressDialog: ProgressDialog
    lateinit var ImageUri: Uri
    private var myUrl = ""
    private lateinit var uploadButton: Button
    private val PICK_IMAGE_REQUEST = 71
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correction)
        setSupportActionBar(toolbar4)
        val nav_View : NavigationView = findViewById(R.id.bottomNavigationView)
        nav_View.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home ->{
                    this.startActivity(Intent(this,HomeActivity::class.java))

                }
                else -> super.onOptionsItemSelected(it)
            }
            when (it.itemId) {
                R.id.nav_Profile ->{
                    this.startActivity(Intent(this,ProfileActivity::class.java))

                }
                else -> super.onOptionsItemSelected(it)
            }
            true
        }

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        progressDialog = ProgressDialog(this)
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference.child("students")



        uploadButton = findViewById(R.id.btnUpload)
        val fab: View = findViewById(R.id.fab)


        fab.setOnClickListener {
            launchGallery()
        }

        uploadButton.setOnClickListener {
            uploadImage()
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
            imageViewResults.setImageURI(ImageUri)
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

                }).addOnCompleteListener (
                    OnCompleteListener<Uri> { task ->
                        if (task.isSuccessful) {
                            val downloadUrl = task.result
                            myUrl = downloadUrl.toString()

                            val ref = FirebaseDatabase.getInstance().reference.child("StudentsTable")
                            val userMap = HashMap<String, Any>()
                            userMap["image"] = myUrl
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

}



