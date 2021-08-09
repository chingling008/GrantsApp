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
import com.example.grantzapp.activity.DonorMeetingActivity
import com.example.grantzapp.activity.GrantsAwardActivity
import com.example.grantzapp.databinding.MeetingLayoutBinding
import com.example.grantzapp.models.Donors

class MeetingAdapter(
    var c: Context, var MeetingList: ArrayList<Donors>
): RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder>() {
    inner class MeetingViewHolder(var v: MeetingLayoutBinding) : RecyclerView.ViewHolder(v.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<MeetingLayoutBinding>(
            inflter,
            R.layout.meeting_layout,
            parent,
            false
        )
        return MeetingViewHolder(v)
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        holder.v.isDonor = MeetingList[position]
        holder.v.root.setOnClickListener {
            val value = MeetingList.get (position)

            val fname = value.getFirst()
            val lname = value.getlast()
            val email = value.getemail3()
            val key= value.getId()

            val image = value.getimage3()


            Toast.makeText(c,key,Toast.LENGTH_SHORT).show()




            var intent = Intent(c, DonorMeetingActivity::class.java)
            intent.putExtra("FirstName", fname)
            intent.putExtra("LastName", lname)
            intent.putExtra("Email", email)
            intent.putExtra("uid", key)

//            Toast.makeText(c, email, Toast.LENGTH_SHORT).show()
            Toast.makeText(c, key, Toast.LENGTH_SHORT).show()

            c.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return MeetingList.size

    }
}


