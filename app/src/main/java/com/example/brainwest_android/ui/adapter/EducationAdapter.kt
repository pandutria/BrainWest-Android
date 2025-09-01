package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.databinding.ItemEducationBinding

class EducationAdapter(
    private val educationList: MutableList<Education> = mutableListOf(),
    private val onClick: (Education) -> Unit
): RecyclerView.Adapter<EducationAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemEducationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (education: Education, position: Int, onClick: (Education) -> Unit) {
            binding.tvTitle.text = education.title
            binding.tvDesc.text = education.desc

            Glide.with(binding.root.context)
                .load(education.thumbnail)
                .into(binding.imgEducation)

            if (education.category == "video") {
                binding.imgPlay.visibility = View.VISIBLE
                binding.overlay.visibility = View.VISIBLE
            } else {
                binding.imgPlay.visibility = View.GONE
                binding.overlay.visibility = View.GONE
            }
            binding.root.setOnClickListener {
                onClick(education)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEducationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(educationList[position], position, onClick)
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