package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.EventTransaction
import com.example.brainwest_android.data.model.RehabilitationVideo
import com.example.brainwest_android.databinding.ItemRehabilitationBinding

class RehabilitationAdapter(
    private val list: MutableList<RehabilitationVideo> = mutableListOf(),
    private val onClick: (RehabilitationVideo) -> Unit
): RecyclerView.Adapter<RehabilitationAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemRehabilitationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(rehabilitationVideo: RehabilitationVideo, onClick: (RehabilitationVideo) -> Unit) {
            binding.tvTitle.text = rehabilitationVideo.title
            Glide.with(binding.root.context)
                .load(rehabilitationVideo.thumbnail)
                .into(binding.imgImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRehabilitationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<RehabilitationVideo>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}