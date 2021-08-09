package com.example.grantzapp.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.grantzapp.databinding.ActivityTotalGrantBinding
import com.example.grantzapp.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class TotalGrantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTotalGrantBinding
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var progressDialog: ProgressDialog
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var storageReference: StorageReference? = null
    private var firebaseStore: FirebaseStorage? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTotalGrantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageReference = FirebaseStorage.getInstance().reference
        firebaseStore = FirebaseStorage.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        firebaseStore = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance().reference

        binding.txtAmount.setOnClickListener {
            val Amount: String = binding.txtAmount.text.toString()

            if (Amount.isEmpty()) {
                Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnGrants.setOnClickListener {
            val amount: String = binding.txtAmount.text.toString()
            Savenew(amount)
        }
    }
    fun Savenew(amount: String) {
        val donations = Donations(amount)
        database.child("Donations").child("amount").setValue(amount)

        Toast.makeText(this, "uploaded successfully", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
        finish()
        progressDialog.dismiss()

    }




}


