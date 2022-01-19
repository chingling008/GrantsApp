package com.example.grantzapp.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.grantzapp.R
import com.example.grantzapp.models.Receipt
import com.example.grantzapp.models.Results
import com.example.grantzapp.models.Students
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.results.*
import kotlinx.android.synthetic.main.student_details.*
import java.util.*
import kotlin.collections.ArrayList

class CorrectionActivity : AppCompatActivity(), IFirebaseLoadDone {
    lateinit var database: DatabaseReference
    lateinit var iFirebaseLoadDone:IFirebaseLoadDone
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var progressDialog: ProgressDialog
    lateinit var ImageUri: Uri
    private var myUrl = ""
    private lateinit var spinner:Spinner
    private lateinit var uploadButton: Button
    private lateinit var reportss:TextView
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
        storageReference = FirebaseStorage.getInstance().reference
        database = FirebaseDatabase.getInstance().reference
        database = FirebaseDatabase.getInstance().getReference("StudentsTable")
        iFirebaseLoadDone = this

        database.addValueEventListener(object :ValueEventListener{
            var spinnerList:MutableList<Students> = ArrayList()
            override fun onDataChange(snapshot: DataSnapshot) {
              for (spinnerSnapshot in snapshot.children)
                  spinnerList.add(spinnerSnapshot.getValue(Students::class.java)!!)
                iFirebaseLoadDone.onFirebaseLoadSuccess(spinnerList)
            }

            override fun onCancelled(error: DatabaseError) {
                iFirebaseLoadDone.onFirebaseFailed(error.message)
            }

        })



        uploadButton = findViewById(R.id.btnUpload)
        reportss = findViewById(R.id.txtDesc)
        spinner=findViewById(R.id.SearchableSpinner)
        val fab: View = findViewById(R.id.fab)


        fab.setOnClickListener {
            launchGallery()
        }

        uploadButton.setOnClickListener {


            val Desc: String = reportss.text.toString()
            val spin: String = spinner.toString()
            uploadImage()
            save(Desc)
        }

    }

    private fun save(Desc: String) {
        val ref = FirebaseDatabase.getInstance().reference.child("StudentsTable")
        val userMap = HashMap<String, Any>()
        userMap["Desc"] = Desc
        ref.child(firebaseUser.uid).updateChildren(userMap)
//        val id: String = intent.getStringExtra("key").toString()
//        val user = Results(Desc)
//        database.child("StudentsTable").child(id).child("Desc").setValue(Desc)

//        Toast.makeText(this, "uploaded successfully", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, AdminActivity::class.java)
//        startActivity(intent)
//        finish()
//        progressDialog.dismiss()
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
                            val intent = Intent(this, CorrectionActivity::class.java)
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



