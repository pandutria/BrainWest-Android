package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.CommunityHistory
import com.example.brainwest_android.databinding.ItemHistoryConsultationBinding

class HistoryCommunityAdapter(
    private val list: MutableList<CommunityHistory> = mutableListOf(),
    private val onClick: (CommunityHistory) -> Unit
): RecyclerView.Adapter<HistoryCommunityAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemHistoryConsultationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: CommunityHistory, onClick: (CommunityHistory) -> Unit) {
            binding.tvName.text = history.group.name
            binding.tvLastMessage.text = "Lihat riwayat lengkap, termasuk detail percakapan dan informasi lainnya dengan mengetuk di sini."

            Glide.with(binding.root.context)
                .load(history.group.image_logo)
                .into(binding.imgImage)

            binding.root.setOnClickListener {
                onClick(history)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryConsultationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<CommunityHistory>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}