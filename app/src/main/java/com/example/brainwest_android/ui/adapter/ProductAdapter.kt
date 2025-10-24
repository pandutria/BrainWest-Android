package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.databinding.ItemProductHorizontalBinding
import com.example.brainwest_android.databinding.ItemProductVerticalBinding
import com.example.brainwest_android.utils.FormatRupiah

class ProductAdapter(
    private val list: MutableList<Product> = mutableListOf(),
    private var tagPage: String = "home",
    private val onClick: (Product) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HORIZONTAL = 1
        private const val VIEW_TYPE_VERTICAL = 2
    }

    inner class HorizontalViewHolder(private val binding: ItemProductHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvName.text = product.name
            binding.tvCategory.text = product.category
            binding.tvRating.text = product.rating
            binding.tvPrice.text = FormatRupiah.format(product.price)

            Glide.with(binding.root.context)
                .load(product.image)
                .into(binding.imgImage)

            binding.root.setOnClickListener { onClick(product) }
        }
    }

    inner class VerticalViewHolder(private val binding: ItemProductVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvName.text = product.name
            binding.tvCategory.text = product.category
            binding.tvRating.text = product.rating
            binding.tvPrice.text = FormatRupiah.format(product.price)

            Glide.with(binding.root.context)
                .load(product.image)
                .into(binding.imgImage)

            binding.root.setOnClickListener { onClick(product) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (tagPage == "home") VIEW_TYPE_HORIZONTAL else VIEW_TYPE_VERTICAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HORIZONTAL) {
            val binding = ItemProductHorizontalBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            HorizontalViewHolder(binding)
        } else {
            val binding = ItemProductVerticalBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            VerticalViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = list[position]
        when (holder) {
            is HorizontalViewHolder -> holder.bind(product)
            is VerticalViewHolder -> holder.bind(product)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setData(data: List<Product>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun setPage(page: String) {
        tagPage = page
    }
}
