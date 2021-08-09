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
import com.example.grantzapp.databinding.ActivityContributionBinding
import com.example.grantzapp.databinding.TotalDetailsBinding

import com.example.grantzapp.models.Donated
import com.example.grantzapp.models.Donations
import com.example.grantzapp.models.Donors


class DonationsAdapter (
    var c: Context, var DonationList: ArrayList<Donated>
): RecyclerView.Adapter<DonationsAdapter.DonorsSViewHolder>() {
    inner class DonorsSViewHolder(var v:TotalDetailsBinding) : RecyclerView.ViewHolder(v.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorsSViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<TotalDetailsBinding>(
            inflter,
            R.layout.total_details,
            parent,
            false
        )
        return DonorsSViewHolder(v)
    }
    override fun onBindViewHolder(holder: DonorsSViewHolder, position: Int) {
        holder.v.isDonate= DonationList[position]
        holder.v.root.setOnClickListener {
            val value = DonationList.get (position)

            val amount=value.getamount2()
            var intent = Intent(c, ApproveDonorsActivity::class.java)
            intent.putExtra("amount", amount)
            c.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return DonationList.size

    }
}

