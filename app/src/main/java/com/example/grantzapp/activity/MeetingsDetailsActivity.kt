package com.example.grantzapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.adapters.DonorMeetingAdapter
import com.example.grantzapp.adapters.DonorsAdapter
import com.example.grantzapp.adapters.MeetingAdapter
import com.example.grantzapp.adapters.StudentsAdapter
import com.example.grantzapp.models.Donors
import com.example.grantzapp.models.Students
import com.google.firebase.database.*

class MeetingsDetailsActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var MeetingList: ArrayList<Donors>
    private lateinit var meetingAdapter: DonorMeetingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meetings_details)

        recyclerView = findViewById(R.id.recyclerView5)

        MeetingList = ArrayList()
        meetingAdapter = DonorMeetingAdapter(this,MeetingList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = meetingAdapter

        getmeetingData()
    }

    private fun getmeetingData() {
        database = FirebaseDatabase.getInstance().getReference().child("Bookings")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    for (donorsSnapshot in snapshot.children){
                        val meeting = donorsSnapshot.getValue(Donors::class.java)
                        MeetingList.add(meeting!!)
                    }
                recyclerView.adapter = meetingAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MeetingsDetailsActivity,error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


}