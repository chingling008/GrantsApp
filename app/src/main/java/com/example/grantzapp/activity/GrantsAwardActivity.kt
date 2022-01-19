package com.example.grantzapp.activity

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.grantzapp.R
import com.example.grantzapp.databinding.ActivityGrantsAwardBinding
import com.example.grantzapp.models.Amounts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_grants_award.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.student_details.*
import java.util.*

class GrantsAwardActivity : AppCompatActivity() {


    private lateinit var firebaseUser: FirebaseUser
    private lateinit var progressDialog: ProgressDialog
    private var Amount = ""
    private var date: String? = null

    private lateinit var binding: ActivityGrantsAwardBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var storageReference: StorageReference? = null
    private var firebaseStore: FirebaseStorage? = null
    private var datePickerDialog: DatePickerDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrantsAwardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        storageReference = FirebaseStorage.getInstance().reference
        firebaseStore = FirebaseStorage.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        firebaseStore = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val selectDate = findViewById<TextView>(R.id.txtDates)


        binding.txtAmount.setOnClickListener {
            val Amount: String = binding.txtAmount.text.toString()

            if (Amount.isEmpty()) {
                Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAward.setOnClickListener {
            val amount: String = binding.txtAmount.text.toString()
            val dates: String = binding.txtDates.text.toString()
            val purpose: String = binding.txtPurpose.text.toString()
            Savenew(amount, dates, purpose)


        }

        date = intent.getStringExtra("Date").toString()
        selectDate!!.text = date
        selectDate!!.setOnClickListener(View.OnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            val month: Int = calendar.get(Calendar.MONTH)
            val year: Int = calendar.get(Calendar.YEAR)
            datePickerDialog = DatePickerDialog(
                this@GrantsAwardActivity,
                { _, year, month, dayOfMonth ->
                    date = dayOfMonth.toString() + "-" + (month + 1) + "-" + year
                    Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
                    selectDate!!.text = date
                    onStart()
                }, day, month, year
            )
            datePickerDialog!!.updateDate(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
                    Calendar.DAY_OF_MONTH
                )
            )
            datePickerDialog!!.datePicker.minDate =
                System.currentTimeMillis() + 3 * 60 * 60 * 1000
            datePickerDialog!!.datePicker.maxDate =
                System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000
            datePickerDialog!!.show()
        })

    }

    fun Savenew(amount: String, Dates: String, Purpose: String) {
        val id: String = intent.getStringExtra("key").toString()
        val user = Amounts(amount, id, Dates, Purpose)
        if (id != null) {
            database.child("StudentsTable").child(id).child("amount").setValue(amount)
            database.child("StudentsTable").child(id).child("Dates").setValue(Dates)
            database.child("StudentsTable").child(id).child("Purpose").setValue(Purpose)
        }
        val time = txtAmount.getText().toString()
        val builder = AlertDialog.Builder(this@GrantsAwardActivity)
            .setTitle("Award Grants")
            .setMessage("Confirm you want to award $time?")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()


                Toast.makeText(this, "uploaded successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()
                progressDialog.dismiss()

            })
        builder.show()

    }
}






