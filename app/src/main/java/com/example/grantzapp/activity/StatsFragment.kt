package com.example.grantzapp.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.grantzapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import io.grpc.InternalChannelz.id

class StatsFragment : Fragment() {
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var database: DatabaseReference
    private lateinit var studentsNo:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        database = FirebaseDatabase.getInstance().reference
        studentsNo.findViewById<TextView>(R.id.StudentsNo)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stats, container, false)


        return view
        getTotalStudents()
    }
    private fun getTotalStudents(){
        val studentsRef = firebaseUser.uid.let{it1->
            FirebaseDatabase.getInstance().reference.child("Students")

        }
        studentsRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    studentsNo.text = snapshot.childrenCount.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}