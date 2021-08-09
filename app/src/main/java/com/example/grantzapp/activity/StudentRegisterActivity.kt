package com.example.grantzapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grantzapp.databinding.ActivityStudentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_grants_award.*
import kotlinx.android.synthetic.main.activity_student_register.*

class StudentRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentRegisterBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)

        binding.RegisterStudent.setOnClickListener {
            val firstName: String = binding.txtFirstName.text.toString()
            val lastName: String = binding.txtLastName.text.toString()
            val email: String = binding.txtEmail.text.toString().trim()
            val school: String = binding.txtSchool.text.toString()
            val admNo: String = binding.txtAdmnNo.text.toString()
            val password: String = binding.txtPassword.text.toString()
            val cPassword: String = binding.txtCpassword.text.toString()




            if (firstName.isEmpty()) {
                Toast.makeText(
                    this@StudentRegisterActivity,
                    "Please enter firstName",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (lastName.isEmpty()) {
                Toast.makeText(
                    this@StudentRegisterActivity,
                    "Please enter lastname",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (email.isEmpty()) {
                Toast.makeText(
                    this@StudentRegisterActivity,
                    "Please enter email",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (school.isEmpty()) {
                Toast.makeText(
                    this@StudentRegisterActivity,
                    "Please enter School Name",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (admNo.isEmpty()) {
                Toast.makeText(
                    this@StudentRegisterActivity,
                    "Please enter Admission NUmber",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password.isEmpty()) {
                Toast.makeText(
                    this@StudentRegisterActivity,
                    "Please enter password",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (cPassword.isEmpty()) {
                Toast.makeText(
                    this@StudentRegisterActivity,
                    "Please confirm password ",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!password.equals(cPassword)) {
                Toast.makeText(
                    this@StudentRegisterActivity,
                    "password mismatched",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                progressDialog.setTitle(("please wait"))
                progressDialog.setMessage("creating an account...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            SaveInformation(
                                firstName,
                                lastName,
                                email,
                                school,
                                admNo,
                                progressDialog
                            )
                        }
                    }
            }
        }
    }

    private fun SaveInformation(
        firstName: String,
        lastName: String,
        email: String,
        school: String,
        AdmNo: String,
        progressDialog: ProgressDialog
    ) {
        val CurrentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("StudentsTable")

        val userMap = HashMap<String, Any>()
        userMap["uid"] = CurrentUserId
        userMap["firstName"] = firstName
        userMap["lastName"] = lastName
        userMap["school"] = school
        userMap["AdmNo"] = AdmNo.toLowerCase()
        userMap["email"] = email
        userMap["amount"] = ""
        userMap["Dates"] = ""
        userMap["Purpose"] = ""
        userMap["image"] = ""

        userRef.child(CurrentUserId).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
                    val intent =
                        Intent(this@StudentRegisterActivity, StudentLoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }.addOnFailureListener {

            }
    }
}