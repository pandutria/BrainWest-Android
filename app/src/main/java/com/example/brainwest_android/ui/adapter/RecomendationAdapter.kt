package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.databinding.ItemRecomendationBinding

class RecomendationAdapter(private val recomendationList: MutableList<String> = mutableListOf())
    : RecyclerView.Adapter<RecomendationAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemRecomendationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (recomendation: String) {
            binding.tvRecomendation.text = recomendation
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecomendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecomendationAdapter.ViewHolder, position: Int) {
        holder.bind(recomendationList[position])
    }

    override fun getItemCount(): Int {
        return recomendationList.size
    }

    fun setData(newData: List<String>) {
        recomendationList.clear()
        recomendationList.addAll(newData)
        notifyDataSetChanged()
    }
}