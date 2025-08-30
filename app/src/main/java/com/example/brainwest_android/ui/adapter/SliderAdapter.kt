package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.databinding.ItemSlideBinding

class SliderAdapter(
    private val items: List<Int>,
    private val icons: List<Int>
) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    class ViewHolder (val binding: ItemSlideBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgSlide.setImageResource(items[position])
        holder.binding.icon.setImageResource(icons[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
