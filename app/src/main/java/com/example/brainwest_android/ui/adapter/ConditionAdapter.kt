package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.databinding.ItemConditionBinding

class ConditionAdapter(private val conditionList: MutableList<String> = mutableListOf())
    : RecyclerView.Adapter<ConditionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemConditionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (condition: String, position: Int) {
            val num = position + 1
            binding.tvCondition.text = "$num. $condition"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConditionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(conditionList[position], position)
    }

    override fun getItemCount(): Int {
        return conditionList.size
    }

    fun setData(newData: List<String>) {
        conditionList.clear()
        conditionList.addAll(newData)
        notifyDataSetChanged()
    }
}