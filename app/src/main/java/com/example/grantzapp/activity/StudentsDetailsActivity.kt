package com.example.grantzapp.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.adapters.StudentsAdapter
import com.example.grantzapp.models.Students
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.Context
import kotlinx.android.synthetic.main.activity_students_details.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.android.synthetic.main.student_details.view.*
import java.util.*
import kotlin.collections.ArrayList

class StudentsDetailsActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference
    private lateinit var recyclerView1: RecyclerView
    private lateinit var  studentsList: ArrayList<Students>
    private lateinit var studentsAdapter: StudentsAdapter
    private lateinit var editSearchText: EditText
    private lateinit var textView: TextView


//    private var mUser:MutableList<Students>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_details)
        recyclerView1 = findViewById(R.id.recyclerView)
        editSearchText = findViewById(R.id.txtSearch)
        textView = findViewById(R.id.textView7)
        studentsList = ArrayList()
        studentsAdapter = StudentsAdapter(this, studentsList, true)
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.setHasFixedSize(true)
        recyclerView1.adapter = studentsAdapter
//        mUser = ArrayList()
        getStudentsData()
//        studentsAdapter = this?.let { StudentsAdapter(StudentsDetailsActivity(), mUser as ArrayList<Students>,true) }

        editSearchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                if (Search.editText.toString() == ""){

                }else{
                    recyclerView1?.visibility = View.VISIBLE

                    getStudentsData()
                    searchUsers(cs.toString().toLowerCase())
                }
//                searchUsers(cs.toString().toLowerCase())

            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
    private fun getStudentsData() {
        val userRef = FirebaseDatabase.getInstance().getReference("StudentsTable")
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (editSearchText!!.text.toString() == "") {
                        studentsList?.clear()
                        for (studentSnapshot in snapshot.children) {
                            val student = studentSnapshot.getValue(Students::class.java)
                            if(student !=null) {
                                studentsList?.add(student)
                            }
//                           studentsList.add(student!!)
                        }
                    }
                    recyclerView.adapter = studentsAdapter
                    studentsAdapter.notifyDataSetChanged();
                    var i = recyclerView.adapter!!.itemCount
                    textView7.setText(String.format("%d",i))
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@StudentsDetailsActivity, error.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    private fun searchUsers(input: String){
        val Query = FirebaseDatabase.getInstance().getReference()
            .child("StudentsTable")
            .orderByChild("AdmNo").startAt(input)
            .endAt(input + "uf8ff")
        Query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                studentsList?.clear()
                for (studentSnapshot in snapshot.children) {
                    val student = studentSnapshot.getValue(Students::class.java)
                    if(student !=null){
                        studentsList?.add(student)
                    }
                }
                studentsAdapter?.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    }





