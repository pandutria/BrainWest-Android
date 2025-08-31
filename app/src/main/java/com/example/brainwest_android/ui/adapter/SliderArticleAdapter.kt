package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.databinding.ItemSlideArticleBinding
import com.example.brainwest_android.databinding.ItemSlideBinding
import com.example.brainwest_android.ui.adapter.SliderAdapter.ViewHolder

class SliderArticleAdapter(
    private val items: List<Int>,
    private val icons: List<Int>,
    private val texts: List<String>
): RecyclerView.Adapter<SliderArticleAdapter.ViewHolder>() {
    class ViewHolder (val binding: ItemSlideArticleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSlideArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgSlide.setImageResource(items[position])
        holder.binding.icon.setImageResource(icons[position])
        holder.binding.tvText.text = texts[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}