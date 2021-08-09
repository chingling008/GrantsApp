package com.example.grantzapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.adapters.MeetingAdapter
import com.example.grantzapp.models.Donors
import com.google.firebase.database.*

class DonorsMeetDetailsActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var meetingList: ArrayList<Donors>
    private lateinit var meetingAdapter: MeetingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donors_meet)
        recyclerView = findViewById(R.id.recyclerView6)
        meetingList = ArrayList()
        meetingAdapter = MeetingAdapter(this,meetingList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = meetingAdapter


        getDonorsData()
    }

    private fun getDonorsData() {
        database = FirebaseDatabase.getInstance().getReference("Donors")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    for (donorsSnapshot in snapshot.children){
                        val donor = donorsSnapshot.getValue(Donors::class.java)
                        meetingList.add(donor!!)

                    }
                recyclerView.adapter = meetingAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DonorsMeetDetailsActivity,error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}
