package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.databinding.ItemArticleBinding
import com.example.brainwest_android.databinding.ItemVideosBinding

class EducationAdapter(
    private val educationList: MutableList<Education> = mutableListOf(),
    private val onClick: (Education) -> Unit
): RecyclerView.Adapter<EducationAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemVideosBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (education: Education, position: Int) {
            binding.tvTitle.text = education.title
            binding.tvDesc.text = education.desc

            if (education.category == "video") {
                binding.imgPlay.visibility = View.VISIBLE
                binding.overlay.visibility = View.VISIBLE
            } else {
                binding.imgPlay.visibility = View.GONE
                binding.overlay.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(educationList[position], position)
    }

    override fun getItemCount(): Int {
        return educationList.size
    }

    fun setData(newData: List<Education>) {
        educationList.clear()
        educationList.addAll(newData)
        notifyDataSetChanged()
    }

}