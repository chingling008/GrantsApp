package com.example.grantzapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.adapters.StudentsDonorsAdapter
import com.example.grantzapp.models.Students
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_students_details.*
import kotlinx.android.synthetic.main.activity_students_details.Search
import kotlinx.android.synthetic.main.activity_students_details.recyclerView
import kotlinx.android.synthetic.main.activity_students_donors.*

class StudentsDonorsActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    private lateinit var recyclerView2: RecyclerView
    private lateinit var  studentDonorList: ArrayList<Students>
    private lateinit var studentsDonorAdapter: StudentsDonorsAdapter
    private lateinit var editSearchText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_donors)

        recyclerView2 = findViewById(R.id.recyclerView10)
        editSearchText = findViewById(R.id.Search)
        studentDonorList = ArrayList()
        studentsDonorAdapter = StudentsDonorsAdapter(this, studentDonorList,true)
        recyclerView2.layoutManager = LinearLayoutManager(this)
        recyclerView2.setHasFixedSize(true)
        recyclerView2.adapter = studentsDonorAdapter
//        mUser = ArrayList()
        getStudentsData()
//        studentsAdapter = this?.let { StudentsAdapter(StudentsDetailsActivity(), mUser as ArrayList<Students>,true) }

        editSearchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                if (Search.text.toString() == ""){

                }else{
                    recyclerView2?.visibility = View.VISIBLE

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
                    studentDonorList?.clear()
                    for (studentSnapshot in snapshot.children) {
                        val student = studentSnapshot.getValue(Students::class.java)
                        if(student !=null) {
                            studentDonorList?.add(student)
                        }
//                           studentsList.add(student!!)
                    }
                }
                recyclerView2.adapter = studentsDonorAdapter
                studentsDonorAdapter.notifyDataSetChanged()
//                var i = recyclerView2.adapter!!.itemCount
//                textView7.setText(String.format("%d",i))
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@StudentsDonorsActivity, error.message, Toast.LENGTH_SHORT)
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
                studentDonorList?.clear()
                for (studentSnapshot in snapshot.children) {
                    val student = studentSnapshot.getValue(Students::class.java)
                    if(student !=null){
                        studentDonorList?.add(student)
                    }
                }
                studentsDonorAdapter?.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
