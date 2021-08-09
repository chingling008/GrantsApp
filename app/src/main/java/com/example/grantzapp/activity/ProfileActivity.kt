package com.example.grantzapp.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.grantzapp.R
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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.results.*
import java.util.HashMap


class ProfileActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private lateinit var firstnameText:TextView
    private lateinit var lastnameText:TextView
    private lateinit var emailText:TextView
    private lateinit var imageText2:ImageView
    private lateinit var amountText:TextView
    private lateinit var datesText:TextView
    private lateinit var purposeText:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

       firstnameText = findViewById(R.id.firstname)
        lastnameText = findViewById(R.id.lastname)
        emailText = findViewById(R.id.email)
       imageText2 = findViewById(R.id.imageView2)
        amountText= findViewById(R.id.Amount)
        datesText= findViewById(R.id.Dates)
        purposeText= findViewById(R.id.Purpose)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("StudentsTable")


        loadProfile()

    }

    private fun loadProfile() {
        val userId = firebaseAuth.currentUser?.uid
        databaseReference?.child(userId!!)?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val firstNameText =snapshot.child("firstName").value.toString()
                   firstnameText.text=firstNameText
                    val lastNameText = snapshot.child("lastName").value.toString()
                    lastnameText.text=lastNameText
                    val email = snapshot.child("email").value.toString()
                    emailText.text=email
                    val image = snapshot.child("image").value.toString()
                    val amount = snapshot.child("amount").value.toString()
                    amountText.text=amount
                    val dates = snapshot.child("Dates").value.toString()
                    datesText.text=dates
                    val purpose = snapshot.child("Purpose").value.toString()
                    purposeText.text=purpose

                    if(image == ""){

                        Picasso.get()
                            .load(R.drawable.ic_launcher_background)
                            .into(imageText2)
                    }else
                    {
                        Picasso.get()
                            .load(image)
                            .into(imageText2)
                    }
                }
//                else{
//                    Toast.makeText(this@ProfileActivity, "no database", Toast.LENGTH_SHORT).show()
//                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}