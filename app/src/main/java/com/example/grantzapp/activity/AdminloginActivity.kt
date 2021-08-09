package com.example.grantzapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grantzapp.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_adminlogin.*


class AdminloginActivity : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog
    var reference: DatabaseReference? = null
    var email_txt: String? = null
    var pass_txt:String? = null
    var adminmail:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogin)

        val email_txt = findViewById<EditText>(R.id.txtEmail)
        val btnLogin = findViewById<Button>(R.id.LoginAdmin)
        val password2 = findViewById<EditText>(R.id.txtPassword)

        reference = FirebaseDatabase.getInstance().getReference("Admin")

        btnLogin.setOnClickListener {
            checkEmpty()
        }

    }

    private fun checkEmpty() {
        email_txt = txtEmail.getText().toString().trim()
        pass_txt= txtPassword.getText().toString()
        if (TextUtils.isEmpty(email_txt)) {
            Toast.makeText(this, "please enter email", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(pass_txt)) {
            Toast.makeText(this, "please enter password", Toast.LENGTH_LONG).show();
        } else {

            progressDialog = ProgressDialog(this)
            progressDialog.setTitle(("please wait"))
            progressDialog.setMessage("Logging in...")
            progressDialog.setCanceledOnTouchOutside(false)


            reference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                        for (studentSnapshot in snapshot.children) {
                            val email_data = snapshot.child("email").value.toString().trim()
                            val pass_data = snapshot.child("password").value.toString()

                            if(email_data.equals(email_txt) && pass_data.equals(pass_txt)){
                                progressDialog.dismiss();
                                Toast.makeText(this@AdminloginActivity, "Login successful", Toast.LENGTH_SHORT)
                                    .show()

                                val intent = Intent(this@AdminloginActivity, AdminActivity::class.java)
                                startActivity(intent)
                                finish()


                            }else{
                                Toast.makeText(this@AdminloginActivity, "Login Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AdminloginActivity, error.message, Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }
    }
}
