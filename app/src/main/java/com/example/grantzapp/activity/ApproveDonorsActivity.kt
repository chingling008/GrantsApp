package com.example.grantzapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grantzapp.databinding.ActivityApproveDonorsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.collections.HashMap


class ApproveDonorsActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var progressDialog: ProgressDialog
    lateinit var ImageUri: Uri
    private var myUrl = ""
    private lateinit var uploadButton: Button
    private val PICK_IMAGE_REQUEST = 71
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var binding: ActivityApproveDonorsBinding
    var reference: DatabaseReference? = null

    var result=""
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApproveDonorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        firebaseStore = FirebaseStorage.getInstance()
        val email1:String = intent.getStringExtra("email").toString()
        val fname:String = intent.getStringExtra("firstName").toString()
        val lname:String = intent.getStringExtra("lastName").toString()
        val ntnId:String = intent.getStringExtra("Image").toString()
        val wid:String = intent.getStringExtra("uid").toString()

        val randomgenerator = Randomgenerator()
        result=randomgenerator.generateAlphaNumeric(8)

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()

        binding.fnamet.setText(fname)
        binding.lnamet.setText(lname)
        binding.emailt.setText(email1)
        Picasso.get().load(ntnId).into(binding.ntdImg)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        binding.submit.setOnClickListener {
            SaveInformation(fname, lname, email1, ntnId, result)
            sendEmail(email1,result)
            deleteDonors(wid)
            val intent = Intent(this, DonorsDetailsActivity::class.java)
            startActivity(intent)
            }
        }

    private fun SaveInformation(
        fname: String,
        lname: String,
        email1: String,
        ntnId: String,
        result: String
    ) {
        auth!!.createUserWithEmailAndPassword(email1, result).addOnCompleteListener { task->
            if (task.isSuccessful){
                reference=FirebaseDatabase.getInstance().getReference()
            }
                val CurrentUserId = auth!!.currentUser!!.uid
                val hashMap: HashMap<String, Any> = HashMap()
                hashMap["FirstName"] = fname
                hashMap["Email"] = email1
                hashMap["LastName"] = lname
                hashMap["Image"] = ntnId
                hashMap["uid"]=CurrentUserId
                hashMap["password"]=result

            reference?.child("Donors")?.child(CurrentUserId)?.setValue(hashMap)
                ?.addOnCompleteListener { task->
                    if (task.isSuccessful){
                Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT).show()

                    }

                }

            }
        }

}
    private fun deleteDonors(wid: String) {
        val drStudent = FirebaseDatabase.getInstance().getReference("Users").child(wid)
        drStudent.removeValue()
    }
private fun sendEmail(email1: String, result: String) {
        val username = "kimathialex54@gmail.com"
        val password = "Alexkim2?"
        val emailFrom = "kimathialex54@gmail.com"
        val emailTo = email1



        val subject = "Login Details"
        val text = "Hello kindly use "+ email1 + "and password : " +result +" to login"

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


