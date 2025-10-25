package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.data.model.EventTransaction
import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.data.model.ProductTransactionHeader
import com.example.brainwest_android.data.repository.ProductTransactionRepository
import com.example.brainwest_android.databinding.ItemHistoryProductHeaderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class HistoryTransactionHeaderProductAdapter(
    private val list: MutableList<ProductTransactionHeader> = mutableListOf(),
    private val onClick: (ProductTransactionHeader) -> Unit
): RecyclerView.Adapter<HistoryTransactionHeaderProductAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemHistoryProductHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(header: ProductTransactionHeader, onClick: (ProductTransactionHeader) -> Unit) {
            binding.tvDate.text = header.created_at.toIndonesianDate()

            val adapter = HistoryTransactionDetailProductAdapter()
            binding.rvProduct.adapter = adapter
            val repo = ProductTransactionRepository()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val res = repo.getTransactionDetail()
                    if (res.isSuccessful) {
                        val data = res.body()?.data ?: emptyList()
                        withContext(Dispatchers.Main) {
                            adapter.setData(data.filter { x -> x.product_transaction_header_id == header.id })
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryProductHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<ProductTransactionHeader>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun String.toIndonesianDate(): String {
        return try {
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
            input.timeZone = TimeZone.getTimeZone("UTC")
            val date = input.parse(this)
            val output = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
            output.format(date)
        } catch (e: Exception) {
            "-"
        }
    }

}