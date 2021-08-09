package com.example.grantzapp.activity


import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.grantzapp.R
import com.example.grantzapp.models.Donors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_donor_meeting.*
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class DonorMeetingActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var date: String? = null
    private var name: String?=null
    private lateinit var meetingList: ArrayList<Donors>
    private  var studentName: String? = null
    private lateinit var database: DatabaseReference
    var result=""

    private var calendar: Calendar? = null
    private var datePickerDialog: DatePickerDialog? = null
    var ref: DatabaseReference? = null
    var refs: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_meeting)
        firebaseAuth = FirebaseAuth.getInstance()
        meetingList = ArrayList()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        database = FirebaseDatabase.getInstance().reference

        val t1 = findViewById<TextView>(R.id.t1)
        val t2 = findViewById<TextView>(R.id.t2)
        val t3 = findViewById<TextView>(R.id.t3)
        val selectDate = findViewById<TextView>(R.id.bookAppointment_selectDate)


        ref = FirebaseDatabase.getInstance().getReference().child("Bookings")


        date = intent.getStringExtra("Date").toString()
        selectDate!!.text = date
        selectDate!!.setOnClickListener(View.OnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            val month: Int = calendar.get(Calendar.MONTH)
            val year: Int = calendar.get(Calendar.YEAR)
            datePickerDialog = DatePickerDialog(
                this@DonorMeetingActivity,
                { _, year, month, dayOfMonth ->
                    date = dayOfMonth.toString() + "-" + (month + 1) + "-" + year
                    Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
                    selectDate!!.text = date
                    onStart()
                    t1.setVisibility(View.VISIBLE)
                    t2.setVisibility(View.VISIBLE)
                    t3.setVisibility(View.VISIBLE)

                    check()
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



        t1.setOnClickListener {
            refs = FirebaseDatabase.getInstance().getReference("StudentsTable")
            val user = firebaseAuth.currentUser?.uid
            refs!!.child(user!!).addValueEventListener( object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val email12=snapshot.child("email").getValue().toString()
                    val time = t1.getText().toString()
                    val builder = AlertDialog.Builder(this@DonorMeetingActivity)
                        .setTitle("Book")
                        .setMessage("Confirm Booking $time?")
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                            val reference = FirebaseDatabase.getInstance().reference
                            val id = reference.push().key
                            val userId = firebaseAuth.currentUser?.uid
                            val email1:String = intent.getStringExtra("email").toString()
                            val wid:String = intent.getStringExtra("uid").toString()
                            val name:String = intent.getStringExtra("firstName").toString()
                            val hashmap: HashMap<String, Any> = HashMap()
                            hashmap["time"] = time
                            hashmap["dates"] = date!!
                            hashmap["studentId"]=userId!!
                            hashmap["donorid"]=wid
                            hashmap["donoremail"]=email1
                            hashmap["donorname"]=name
                            hashmap["studentemail"]=email12
                            reference.child("Bookings").child(id!!).setValue(hashmap)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        this@DonorMeetingActivity,
                                        "success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    sendEmail(email1, name, date!!, time, email12)
                                }
                        })
                        .create()
                    builder.show()
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
        t2.setOnClickListener {
            refs = FirebaseDatabase.getInstance().getReference("StudentsTable")
            val user = firebaseAuth.currentUser?.uid
            refs!!.child(user!!).addValueEventListener( object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val email12=snapshot.child("email").getValue().toString()
                    val time = t2.getText().toString()
                    val builder = AlertDialog.Builder(this@DonorMeetingActivity)
                        .setTitle("Book")
                        .setMessage("Confirm Booking $time?")
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                            val reference = FirebaseDatabase.getInstance().reference
                            val id = reference.push().key
                            val userId = firebaseAuth.currentUser?.uid
                            val email1:String = intent.getStringExtra("email").toString()
                            val wid:String = intent.getStringExtra("uid").toString()
                            val name:String = intent.getStringExtra("firstName").toString()
                            val hashmap: HashMap<String, Any> = HashMap()
                            hashmap["time"] = time
                            hashmap["dates"] = date!!
                            hashmap["studentId"]=userId!!
                            hashmap["donorid"]=wid
                            hashmap["donoremail"]=email1
                            hashmap["donorname"]=name
                            hashmap["studentemail"]=email12
                            reference.child("Bookings").child(id!!).setValue(hashmap)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        this@DonorMeetingActivity,
                                        "success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    sendEmail(email1,name, date!!,time, email12)
                                }
                        })
                        .create()
                    builder.show()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }
        t3.setOnClickListener {
            refs = FirebaseDatabase.getInstance().getReference("StudentsTable")
            val user = firebaseAuth.currentUser?.uid
            refs!!.child(user!!).addValueEventListener( object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val email12=snapshot.child("email").getValue().toString()
                    val time = t3.getText().toString()
                    val builder = AlertDialog.Builder(this@DonorMeetingActivity)
                        .setTitle("Book")
                        .setMessage("Confirm Booking $time?")
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                            val reference = FirebaseDatabase.getInstance().reference
                            val id = reference.push().key
                            val userId = firebaseAuth.currentUser?.uid
                            val email1:String = intent.getStringExtra("email").toString()
                            val wid:String = intent.getStringExtra("uid").toString()
                            val name:String = intent.getStringExtra("firstName").toString()
                            val hashmap: HashMap<String, Any> = HashMap()
                            hashmap["time"] = time
                            hashmap["dates"] = date!!
                            hashmap["studentId"]=userId!!
                            hashmap["donorid"]=wid
                            hashmap["donoremail"]=email1
                            hashmap["donorname"]=name
                            hashmap["studentemail"]=email12
                            reference.child("Bookings").child(id!!).setValue(hashmap)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        this@DonorMeetingActivity,
                                        "success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    sendEmail(email1, name, date!!, time, email12)
                                }
                        })
                        .create()
                    builder.show()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }


    }




    private fun check() {
        ref?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {

                    val book = dataSnapshot.getValue(Donors::class.java)
                    val dates: String? = book?.getdates1()
                    val times: String? = book?.gettime1()

                    val t1time = t1.text.toString()
                    val t2time = t2.text.toString()
                    val t3time = t3.text.toString()

                    if (date.equals(dates) && times.equals(t1time)) {
                        t1.setVisibility(View.GONE);
                    } else if (date.equals(dates) && times.equals(t2time)) {
                        t2.setVisibility(View.GONE);
                    } else if (date.equals(dates) && times.equals(t3time)) {
                        t3.setVisibility(View.GONE);
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@DonorMeetingActivity, error.message, Toast.LENGTH_SHORT).show()
            }



        }
        )
    }
    private fun sendEmail(result: String, name: String, date: String, time: String, email12: String) {
        val username = "kimathialex54@gmail.com"
        val password = "Alexkim2?"
        val emailFrom = "kimathialex54@gmail.com"
        val emailTo = result
        val subject = "Meeting with a Student"
        val text = "Hello"  +name+   "You have a meeting on "  +date+  "Time:" +time +
                "Student  Email"  +email12

        val props = Properties()
        putIfMissing(props, "mail.smtp.host", "smtp.gmail.com")
        putIfMissing(props, "mail.smtp.port", "587")
        putIfMissing(props, "mail.smtp.auth", "true")
        putIfMissing(props, "mail.smtp.starttls.enable", "true")

        val session = Session.getDefaultInstance(props, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })
        session.debug = true

        try {
            val mimeMessage = MimeMessage(session)
            mimeMessage.setFrom(InternetAddress(emailFrom))
            mimeMessage.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(emailTo, false)
            )

            mimeMessage.setText(text)
            mimeMessage.subject = subject
            mimeMessage.sentDate = Date()

            val smtpTransport = session.getTransport("smtp")
            smtpTransport.connect()
            smtpTransport.sendMessage(mimeMessage, mimeMessage.allRecipients)
            smtpTransport.close()
        } catch (messagingException: MessagingException) {
            messagingException.printStackTrace()
        }
    }
    private fun putIfMissing(props: Properties, key: String, value: String) {
        if (!props.containsKey(key)) {
            props[key] = value
        }
    }
}






