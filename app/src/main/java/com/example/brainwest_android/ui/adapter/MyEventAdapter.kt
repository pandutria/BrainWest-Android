package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.data.model.EventTransaction
import com.example.brainwest_android.databinding.ItemMyEventBinding
import com.example.brainwest_android.utils.FormatRupiah
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class MyEventAdapter(
    private val eventList: MutableList<EventTransaction> = mutableListOf(),
    private val onClick: (EventTransaction) -> Unit
): RecyclerView.Adapter<MyEventAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMyEventBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(eventTransaction: EventTransaction, onClick: (EventTransaction) -> Unit) {
            binding.tvTitle.text = eventTransaction.event!!.title
            binding.tvDate.text = eventTransaction.event.date
            binding.tvTime.text = eventTransaction.event.timestamp
            binding.tvAddress.text = eventTransaction.event.address
            binding.tvPrice.text = FormatRupiah.format(eventTransaction.event.price!!)

            val sdf = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))
            val eventDate = sdf.parse(eventTransaction.event.date)
            val today = Calendar.getInstance().time
            val diffInMillis = eventDate.time - today.time
            val daysBetween = TimeUnit.MILLISECONDS.toDays(diffInMillis)

            if (daysBetween >= 0) {
                binding.tvRestDay.text = "$daysBetween Hari Lagi"
            } else {
                binding.tvRestDay.text = "Sudah Berlalu"
            }

            Glide.with(binding.root.context)
                .load(eventTransaction.event.image)
                .into(binding.imgThumbnail)

            binding.root.setOnClickListener {
                onClick(eventTransaction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position], onClick)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun setData(newData: List<EventTransaction>) {
        eventList.clear()
        eventList.addAll(newData)
        notifyDataSetChanged()
    }
}