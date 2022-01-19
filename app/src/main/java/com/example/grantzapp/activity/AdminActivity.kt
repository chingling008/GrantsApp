package com.example.grantzapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.grantzapp.R
import com.example.grantzapp.databinding.ActivityAdminBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.content_main.*


class AdminActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var binding:ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                R.id.nav_SProfile ->{
                    this.startActivity(Intent(this,StudentsDonorsActivity::class.java))
                }
                else -> super.onOptionsItemSelected(it)
            }
            true
        }

        database = FirebaseDatabase.getInstance().reference

        binding.btnStDetails.setOnClickListener {
            val intent = Intent(this, StudentsDetailsActivity::class.java)
            startActivity(intent)
        }
        binding.btnReceipt.setOnClickListener {
            val intent = Intent(this, FileReceiptActivity::class.java)
            startActivity(intent)
        }
        binding.btnGrant.setOnClickListener {
            val intent = Intent(this, TotalGrantActivity::class.java)
            startActivity(intent)
        }

        binding.btnApprove.setOnClickListener {
            val intent = Intent(this, DonorsDetailsActivity::class.java)
            startActivity(intent)
        }
        binding.btnbal.setOnClickListener {
            val intent = Intent(this,AdminBalActivity::class.java)
            startActivity(intent)
        }

 
    }



}