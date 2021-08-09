package com.example.grantzapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.adapters.DonationsAdapter
import com.example.grantzapp.models.Donated
import com.example.grantzapp.models.Donations
import com.google.firebase.database.*

class ContributionActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var donationList: ArrayList<Donated>
    private lateinit var donationAdapter: DonationsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contribution)

        recyclerView = findViewById(R.id.recyclerView9)

        donationList = ArrayList()
        donationAdapter = DonationsAdapter(this,donationList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = donationAdapter


        getTotal()
    }

    private fun getTotal() {
        database = FirebaseDatabase.getInstance().getReference().child("Donations")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    for (donorsSnapshot in snapshot.children){
                        val donor = donorsSnapshot.getValue(Donated::class.java)
                        donationList.add(donor!!)
                    }
                recyclerView.adapter = donationAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ContributionActivity,error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}
