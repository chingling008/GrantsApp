package com.example.grantzapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.grantzapp.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_main.*


class  HomeActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar4)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_Layout)
        val nav_View : NavigationView = findViewById(R.id.nav_View)
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        nav_View.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_Admin ->{
                    this.startActivity(Intent(this,AdminloginActivity::class.java))

                }
                else -> super.onOptionsItemSelected(it)
            }
            when (it.itemId) {
                R.id.nav_home ->{
                    this.startActivity(Intent(this,HomeActivity::class.java))

                }
                else -> super.onOptionsItemSelected(it)
            }
            true
        }

        val button = findViewById<Button>(R.id.btnStudent)
        button.setOnClickListener {
            val intent = Intent(this, StudentLoginActivity::class.java)
            startActivity(intent)
        }

        val button1 = findViewById<Button>(R.id.btnDonor)
        button1.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
