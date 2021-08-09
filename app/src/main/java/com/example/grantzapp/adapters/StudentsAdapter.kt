package com.example.grantzapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.activity.GrantsAwardActivity
import com.example.grantzapp.activity.StudentsDetailsActivity
import com.example.grantzapp.databinding.StudentDetailsBinding
import com.example.grantzapp.models.Students


class StudentsAdapter(
   var c: StudentsDetailsActivity, var StudentList: ArrayList<Students>, b: Boolean
):RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder>() {
    inner class StudentsViewHolder(var v: StudentDetailsBinding) : RecyclerView.ViewHolder(v.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<StudentDetailsBinding>(
                inflter,
                R.layout.student_details,
                parent,
                false
        )
        return StudentsViewHolder(v)
    }

    override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {
        holder.v.isStudents = StudentList[position]
        holder.v.root.setOnClickListener {
          val value = StudentList.get (position)
            val key=value.getId()
            Toast.makeText(c,key,Toast.LENGTH_SHORT).show()

           // var key=getItemId(position)
            var intent = Intent(c, GrantsAwardActivity::class.java)
            intent.putExtra("key", key)
            c.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return StudentList.size

    }
//    fun updateList(list: MutableList<Students>){
//        StudentList= list as ArrayList<Students>
//        notifyDataSetChanged()
//
//    }



}

