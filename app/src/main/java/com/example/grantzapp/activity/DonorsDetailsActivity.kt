package com.example.grantzapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.adapters.DonorsAdapter

import com.example.grantzapp.models.Donors

import com.google.firebase.database.*


class DonorsDetailsActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var donorsList: ArrayList<Donors>
    private lateinit var donorsAdapter: DonorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donors_details)
        recyclerView = findViewById(R.id.recyclerView4)

        donorsList = ArrayList()
        donorsAdapter = DonorsAdapter(this,donorsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = donorsAdapter


        getDonorsData()
    }

    private fun getDonorsData() {
        database = FirebaseDatabase.getInstance().getReference().child("Users")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    for (donorsSnapshot in snapshot.children){
                        val donor = donorsSnapshot.getValue(Donors::class.java)
                        donorsList.add(donor!!)
                    }
                recyclerView.adapter = donorsAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DonorsDetailsActivity,error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}