package com.example.grantzapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.grantzapp.R
import com.example.grantzapp.databinding.StudentsDonorsDetailsBinding
import com.example.grantzapp.models.Students

class StudentsDonorsAdapter(
    var c: Context, var StudentDonorList: ArrayList<Students>, b: Boolean
): RecyclerView.Adapter<StudentsDonorsAdapter.StudentssViewHolder>() {
    inner class StudentssViewHolder(var v: StudentsDonorsDetailsBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentssViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate< StudentsDonorsDetailsBinding>(
            inflter,
            R.layout.students_donors_details,
            parent,
            false
        )
        return StudentssViewHolder(v)
    }

    override fun onBindViewHolder(holder: StudentssViewHolder, position: Int) {
        holder.v.isStudentDonor = StudentDonorList[position]
        holder.v.root.setOnClickListener {
            val value = StudentDonorList.get (position)

            val fname=value.getfirstName1()
            val lname = value.getlastName1()
            val email = value.getemail3()
            val dates = value.getDates4()
            val school = value.getschool1()
            val purpose = value.getpurpose1()
            val amount = value.getamount4()
            val key= value.getId()


            var intent = Intent(c, this::class.java)
            intent.putExtra("firstName", fname)
            intent.putExtra("lastName", lname)
            intent.putExtra("email", email)
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
        return StudentDonorList.size

    }
}

