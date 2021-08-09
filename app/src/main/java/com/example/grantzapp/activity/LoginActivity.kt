package com.example.grantzapp.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.grantzapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginActivity : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.txtEmail2)
        val passwordEditText = findViewById<EditText>(R.id.txtPassword2)
        val btnLogin = findViewById<Button>(R.id.DonorLogin)
        val textView1 = findViewById<TextView>(R.id.textView1)
        textView1.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
            val currentuser = firebaseAuth.currentUser
            if (currentuser != null){
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        btnLogin.setOnClickListener {
            val email = emailEditText.text.toString();
            val password = passwordEditText.text.toString();

            if (email.isEmpty()) {
                Toast.makeText(this, "please provide your email", Toast.LENGTH_SHORT)
            } else if (password.isEmpty()) {
                Toast.makeText(this, "please provide your password", Toast.LENGTH_SHORT)
            }
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, DonorsActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
        }
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(("please wait"))
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)
    }
}
