//package com.example.grantzapp.activity
//
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.EditText
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.grantzapp.R
//import com.example.grantzapp.adapters.StudentsAdapter
//import com.example.grantzapp.models.Students
//import com.google.firebase.database.*
//import kotlinx.android.synthetic.main.activity_students_details.*
//import kotlinx.android.synthetic.main.activity_students_details.Search
//import kotlinx.android.synthetic.main.fragment_search.view.*
//
//class SearchFragment : Fragment() {
//    lateinit var database: DatabaseReference
//    private  var recyclerView1: RecyclerView? = null
//    private lateinit var  studentsList: ArrayList<Students>
//    private  var studentsAdapter: StudentsAdapter? = null
//    private var editSearchText: EditText? = null
//    private var mUser:MutableList<Students>? = null
//
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?
//    ): View? {
//
//        val view = inflater.inflate(R.layout.fragment_search, container, false)
//
//        recyclerView1= view.findViewById(R.id.recyclerView5)
//        recyclerView1?.layoutManager = LinearLayoutManager(context)
//        recyclerView1?.setHasFixedSize(true)
//
//        mUser = ArrayList()
//
//        studentsAdapter = context?.let { StudentsAdapter(StudentsDetailsActivity(), mUser as ArrayList<Students>,true) }
//        recyclerView1?.adapter = studentsAdapter
//
//
//
//
//        view.Search.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//              if (view.Search.text.toString() == ""){
//
//              }else{
//                  recyclerView1?.visibility = View.VISIBLE
//
//                  getStudentsData()
//                  searchUser(s.toString().toLowerCase())
//              }
//            }
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//        return view
//    }
//
//    private fun searchUser(input: String,) {
//        val Query = FirebaseDatabase.getInstance().getReference()
//            .child("StudentsTable")
//                .orderByChild("AdmNo").startAt(input)
//                .endAt(input + "uf8ff")
//        Query.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                mUser?.clear()
//                    for (studentSnapshot in snapshot.children) {
//                        val student = studentSnapshot.getValue(Students::class.java)
//                        if(student !=null){
//                            mUser?.add(student)
//                        }
//                    }
//                    studentsAdapter?.notifyDataSetChanged()
//                }
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })
//    }
//    private fun getStudentsData() {
//        val userRef= FirebaseDatabase.getInstance().getReference("StudentsTable")
//        userRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (Search.text.toString() == "") {
//                    mUser?.clear()
//                        for (studentSnapshot in snapshot.children) {
//                            val student = studentSnapshot.getValue(Students::class.java)
//                            if(student !=null){
//                                mUser?.add(student)
//                            }
//                        }
//                    studentsAdapter?.notifyDataSetChanged()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })
//    }
//
//}
//
