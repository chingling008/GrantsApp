package com.example.grantzapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.activity.ApproveDonorsActivity
import com.example.grantzapp.databinding.ActivityDonorStudentsBinding
import com.example.grantzapp.databinding.DonorStudentsDetailsBinding

import com.example.grantzapp.models.Students

class DonorStudentsAdapter (
    var c: Context, var DonorStudentsList: ArrayList<Students>
): RecyclerView.Adapter<DonorStudentsAdapter.StudentssViewHolder>() {
    inner class StudentssViewHolder(var v: DonorStudentsDetailsBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentssViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<DonorStudentsDetailsBinding>(
            inflter,
            R.layout.donor_students_details,
            parent,
            false
        )
        return StudentssViewHolder(v)
    }

    override fun onBindViewHolder(holder: StudentssViewHolder, position: Int) {
        holder.v.isMM = DonorStudentsList[position]
        holder.v.root.setOnClickListener {
            val value = DonorStudentsList.get (position)

            val fname=value.getfirstName1()
            val lname = value.getlastName1()
            val email = value.getemail3()
            val dates = value.getDates4()
            val school = value.getschool1()
            val purpose = value.getpurpose1()
            val amount = value.getamount4()
            val key= value.getId()
            val image = value.getimage1()

            var intent = Intent(c, this::class.java)
            intent.putExtra("firstName", fname)
            intent.putExtra("lastName", lname)
            intent.putExtra("email", email)
            intent.putExtra("image", image)
            intent.putExtra("Dates", dates)
            intent.putExtra("school", school)
            intent.putExtra("purpose", purpose)
            intent.putExtra("amount", amount)
            intent.putExtra("uid", key)

            Toast.makeText(c, email, Toast.LENGTH_SHORT).show()

//            c.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return DonorStudentsList.size

    }
}

