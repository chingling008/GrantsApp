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
//import com.example.grantzapp.databinding.DonorDetailsBinding
import com.example.grantzapp.databinding.MeetingsDetailsBinding
import com.example.grantzapp.models.Donors

class DonorMeetingAdapter(
    var c: Context, var DonorsList2: ArrayList<Donors>
): RecyclerView.Adapter<DonorMeetingAdapter.DonorsViewHolder>() {
    inner class DonorsViewHolder(var v: MeetingsDetailsBinding) : RecyclerView.ViewHolder(v.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorsViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<MeetingsDetailsBinding>(
            inflter,
            R.layout.meetings_details,
            parent,
            false
        )
        return DonorsViewHolder(v)
    }

    override fun onBindViewHolder(holder: DonorsViewHolder, position: Int) {
        holder.v.isMeeting = DonorsList2[position]
        holder.v.root.setOnClickListener {
            val value = DonorsList2.get (position)

            val fname=value.getFirst()
            val lname = value.getlast()
            val email = value.getemail3()
            val dates = value.getdates1()
            val time = value.gettime1()
            val donorname = value.getdonorname1()
            val donoremail = value.getdonoremail1()
            val studentemail = value.getstudentemail2()
//            val student = value.getstudentId4()
            val key= value.getId()

            val image = value.getimage3()

            var intent = Intent(c, ApproveDonorsActivity::class.java)
            intent.putExtra("firstName", fname)
            intent.putExtra("lastName", lname)
            intent.putExtra("email", email)
            intent.putExtra("Image", image)
            intent.putExtra("dates", dates)
            intent.putExtra("donorname", donorname)
            intent.putExtra("donoremail", donoremail)
            intent.putExtra("studentemail", studentemail)
//            intent.putExtra("studentId", student)
            intent.putExtra("time", time)
            intent.putExtra("uid", key)

            Toast.makeText(c, email, Toast.LENGTH_SHORT).show()

            c.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return DonorsList2.size

    }
}

