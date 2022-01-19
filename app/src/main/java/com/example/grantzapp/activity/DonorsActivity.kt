package com.example.grantzapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grantzapp.R
import com.example.grantzapp.databinding.ActivityDonorsBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*

class DonorsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonorsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDonorsBinding.inflate(layoutInflater)
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


        binding.btnDonate.setOnClickListener {
            val intent = Intent(this, DonorStudentsActivity::class.java)
            startActivity(intent)
        }
        binding.btnAppointment.setOnClickListener {
            val intent = Intent(this,  MeetingsDetailsActivity::class.java)
            startActivity(intent)
        }
        binding.btnReport.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)

        }
//        binding.btnReceipt.setOnClickListener {
//            val intent = Intent(this, ReceiptActivity::class.java)
//            startActivity(intent)
//
//        }
        binding.btnbal.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)

        }
    }
}