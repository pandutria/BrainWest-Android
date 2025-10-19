package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.Community
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.databinding.ItemCommunityBinding

class CommunityAdapter(
    val list: MutableList<Community> = mutableListOf(),
    private val onClick: (Community) -> Unit
): RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemCommunityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(community: Community, onClick: (Community) -> Unit) {
            binding.tvName.text = community.name
            binding.tvMembers.text = "231 Members"

            Glide.with(binding.root.context)
                .load(community.image)
                .into(binding.imgImage)

            Glide.with(binding.root.context)
                .load(community.image_logo)
                .into(binding.imgImageLogo)

            binding.btnJoin.setOnClickListener {
                onClick(community)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newData: List<Community>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
}