package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.databinding.ItemProductBinding
import com.example.brainwest_android.utils.FormatRupiah

class ProductAdapter(
    private val list: MutableList<Product   > = mutableListOf(),
    private val onClick: (Product) -> Unit
): RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {
    class ViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, onClick: (Product) -> Unit) {
            binding.tvName.text = product.name
            binding.tvCategory.text = product.category
            binding.tvRating.text = product.rating
            binding.tvPrice.text = FormatRupiah.format(product.price)

            Glide.with(binding.root.context)
                .load(product.image)
                .into(binding.imgImage)

            binding.root.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<Product>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}