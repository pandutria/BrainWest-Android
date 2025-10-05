package com.example.brainwest_android.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.Doctor
import com.example.brainwest_android.databinding.ItemDoctorBinding

class ConsultationAdapter(
    private val consultationList: MutableList<Doctor> = mutableListOf(),
    private val onClick: (Doctor) -> Unit
): RecyclerView.Adapter<ConsultationAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemDoctorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (doctor: Doctor, onClick: (Doctor) -> Unit) {
            binding.tvName.text = "Dr. ${doctor.user.fullname}"
            binding.tvLocation.text = doctor.hospital
            binding.tvRating.text = "Peringkat Ulasan ${doctor.rating}"
            binding.tvSpecialist.text = doctor.specialization

            Glide.with(binding.root)
                .load(doctor.image)
                .into(binding.imgDoctor)

            binding.btnStart.setOnClickListener {
                onClick(doctor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ViewHolder(holder.binding).bind(consultationList[position], onClick)
    }

    override fun getItemCount(): Int {
        return consultationList.size
    }

    fun setData(data: List<Doctor>) {
        consultationList.clear()
        consultationList.addAll(data)
        notifyDataSetChanged()
    }
}