package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.DonationTransaction
import com.example.brainwest_android.databinding.ItemHistoryTransactionDonationBinding
import com.example.brainwest_android.utils.FormatRupiah

class HistoryTransactionDonationAdapter(
    private val list: MutableList<DonationTransaction> = mutableListOf(),
    private val onClick: (DonationTransaction) -> Unit
): RecyclerView.Adapter<HistoryTransactionDonationAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemHistoryTransactionDonationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(donationTransaction: DonationTransaction, onClick: (DonationTransaction) -> Unit) {
            binding.tvName.text = "Donati untuk ${donationTransaction.donate!!.title}"
            binding.tvPrice.text = FormatRupiah.format(donationTransaction.total_donate!!.toInt())

            Glide.with(binding.root.context)
                .load(donationTransaction.donate.image)
                .into(binding.imgImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryTransactionDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun newData(newData: List<DonationTransaction>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
}