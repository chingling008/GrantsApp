package com.example.grantzapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grantzapp.R
import com.example.grantzapp.databinding.ActivityStudentsBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*


class StudentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar4)
        val nav_View : NavigationView = findViewById(R.id.bottomNavigationView)
        nav_View.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    this.startActivity(Intent(this, StudentsActivity::class.java))
                }
                else -> super.onOptionsItemSelected(it)
            }

            when (it.itemId) {
                R.id.nav_Profile -> {
                    this.startActivity(Intent(this, ProfileActivity::class.java))
                }
                else -> super.onOptionsItemSelected(it)
            }
            true
        }

        binding.btnFunds.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.btnResults.setOnClickListener {
            val intent = Intent(this, CorrectionActivity::class.java)
            startActivity(intent)
        }
        binding.btnMeeting.setOnClickListener {
            val intent = Intent(this, DonorsMeetDetailsActivity::class.java)
            startActivity(intent)

        }

    }
}