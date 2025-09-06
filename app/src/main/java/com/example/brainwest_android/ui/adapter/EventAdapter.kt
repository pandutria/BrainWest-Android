package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.databinding.ItemEventBinding
import com.example.brainwest_android.utils.FormatRupiah

class EventAdapter(
    private val eventList: MutableList<Event> = mutableListOf(),
    private val onClick: (Event) -> Unit
): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event, onClick: (Event) -> Unit) {
            binding.tvTitle.text = event.title
            binding.tvDate.text = event.date
            binding.tvTime.text = event.timestamp
            binding.tvAddress.text = event.address
            binding.tvPrice.text = FormatRupiah.format(event.price!!)

            Glide.with(binding.root.context)
                    .load(event.image)
                    .into(binding.imgThumbnail)

            binding.root.setOnClickListener {
                onClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position], onClick)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun setData(newData: List<Event>) {
        eventList.clear()
        eventList.addAll(newData)
        notifyDataSetChanged()
    }
}