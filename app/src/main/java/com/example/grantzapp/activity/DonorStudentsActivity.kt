package com.example.grantzapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.adapters.DonorStudentsAdapter
import com.example.grantzapp.adapters.DonorsAdapter
import com.example.grantzapp.models.Donors
import com.example.grantzapp.models.Students
import com.google.firebase.database.*

class DonorStudentsActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var donorsList: ArrayList<Students>
    private lateinit var donorsAdapter: DonorStudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_students)

        recyclerView = findViewById(R.id.recyclerView10)

        donorsList = ArrayList()
        donorsAdapter = DonorStudentsAdapter(this,donorsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = donorsAdapter


        getDonorsData()
    }

    private fun getDonorsData() {
        database = FirebaseDatabase.getInstance().getReference().child("StudentsTable")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    for (donorsSnapshot in snapshot.children){
                        val donor = donorsSnapshot.getValue(Students::class.java)
                        donorsList.add(donor!!)
                    }
                recyclerView.adapter = donorsAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DonorStudentsActivity,error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}
