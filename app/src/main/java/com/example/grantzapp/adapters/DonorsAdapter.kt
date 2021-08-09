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
import com.example.grantzapp.databinding.DonorDetailsBinding
import com.example.grantzapp.models.Donors

class DonorsAdapter(
    var c: Context, var DonorsList: ArrayList<Donors>
): RecyclerView.Adapter<DonorsAdapter.DonorsViewHolder>() {
    inner class DonorsViewHolder(var v: DonorDetailsBinding) : RecyclerView.ViewHolder(v.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorsViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<DonorDetailsBinding>(
            inflter,
            R.layout.donor_details,
            parent,
            false
        )
        return DonorsViewHolder(v)
    }
    override fun onBindViewHolder(holder: DonorsViewHolder, position: Int) {
        holder.v.isDonor = DonorsList[position]
        holder.v.root.setOnClickListener {
            val value = DonorsList.get (position)

            val fname=value.getFirst()
            val lname = value.getlast()
            val email = value.getemail3()
            val key= value.getId()

            val image = value.getimage3()

            var intent = Intent(c, ApproveDonorsActivity::class.java)
            intent.putExtra("firstName", fname)
            intent.putExtra("lastName", lname)
            intent.putExtra("email", email)
            intent.putExtra("Image", image)
            intent.putExtra("uid", key)

            Toast.makeText(c, email, Toast.LENGTH_SHORT).show()

            c.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return DonorsList.size

    }
}

