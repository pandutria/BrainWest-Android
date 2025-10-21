package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.data.model.CommunityHistoryMessage
import com.example.brainwest_android.databinding.ItemHistoryConsultationBinding

class HistoryCommunityAdapter(
    private val list: MutableList<CommunityHistoryMessage> = mutableListOf(),
    private val onClick: (CommunityHistoryMessage) -> Unit
): RecyclerView.Adapter<HistoryCommunityAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemHistoryConsultationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: CommunityHistoryMessage, onClick: (CommunityHistoryMessage) -> Unit) {
            binding.tvName.text = history.group.name
            binding.tvLastMessage.text = history.message

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

    fun setData(data: List<CommunityHistoryMessage>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}