package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.ProductTransactionDetail
import com.example.brainwest_android.data.repository.ProductTransactionRepository
import com.example.brainwest_android.databinding.ItemHistoryProductDetailBinding
import com.example.brainwest_android.utils.FormatRupiah
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryTransactionDetailProductAdapter(
    private val list: MutableList<ProductTransactionDetail> = mutableListOf()
): RecyclerView.Adapter<HistoryTransactionDetailProductAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemHistoryProductDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(detail: ProductTransactionDetail) {
            binding.tvName.text = detail.product.name
            binding.tvQty.text = "Jumlah: ${detail.qty}"
            binding.tvPrice.text = FormatRupiah.format(detail.product.price * detail.qty)

            Glide.with(binding.root.context)
                .load(detail.product.image)
                .into(binding.imgImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryProductDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newData: List<ProductTransactionDetail>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
}