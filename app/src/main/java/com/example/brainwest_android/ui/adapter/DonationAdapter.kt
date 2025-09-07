package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.Donation
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.databinding.ItemDonationBinding
import com.example.brainwest_android.utils.FormatRupiah

class DonationAdapter(
    private val donationList: MutableList<Donation> = mutableListOf(),
    private val onClick: (Donation) -> Unit
): RecyclerView.Adapter<DonationAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemDonationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(donation: Donation, onClick: (Donation) -> Unit) {
            binding.tvTitle.text = donation.title
            binding.tvDeadline.text = donation.deadline
            binding.tvCurrentDona.text = FormatRupiah.format(donation.current_donate!!)
            binding.tvInstitution.text = donation.institution

            Glide.with(binding.root.context)
                .load(donation.image)
                .into(binding.imgImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(donationList[position], onClick)
    }

    override fun getItemCount(): Int {
        return donationList.size
    }

    fun setData(newData: List<Donation>) {
        donationList.clear()
        donationList.addAll(newData)
        notifyDataSetChanged()
    }
}