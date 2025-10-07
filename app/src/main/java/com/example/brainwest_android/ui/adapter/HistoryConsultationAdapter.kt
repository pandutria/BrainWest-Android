package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.ConsultationHistoryMessage
import com.example.brainwest_android.databinding.ItemHistoryConsultationBinding

class HistoryConsultationAdapter(
    private val historyList: MutableList<ConsultationHistoryMessage> = mutableListOf(),
    private val onClick: (ConsultationHistoryMessage) -> Unit
): RecyclerView.Adapter<HistoryConsultationAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemHistoryConsultationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: ConsultationHistoryMessage, onClick: (ConsultationHistoryMessage) -> Unit) {
            binding.tvName.text = "Dr. ${history.doctor.user.fullname}"
            binding.tvLastMessage.text = history.last_message

            Glide.with(binding.root)
                .load(history.doctor.image)
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
        ViewHolder(holder.binding).bind(historyList[position], onClick)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun setData(data: List<ConsultationHistoryMessage>) {
        historyList.clear()
        historyList.addAll(data)
        notifyDataSetChanged()
    }
}