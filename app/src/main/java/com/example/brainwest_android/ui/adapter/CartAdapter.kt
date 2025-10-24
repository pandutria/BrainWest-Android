package com.example.brainwest_android.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainwest_android.R
import com.example.brainwest_android.data.local.CartList
import com.example.brainwest_android.data.model.Cart
import com.example.brainwest_android.databinding.ItemCartBinding
import com.example.brainwest_android.databinding.ItemCommunityBinding
import com.example.brainwest_android.ui.product.cart.CartProductFragment
import com.example.brainwest_android.utils.FormatRupiah

class CartAdapter(
    private val list: MutableList<Cart> = mutableListOf(),
    val fragment: CartProductFragment,
    private val onClick: (Cart) -> Unit
): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(cart: Cart, onClick: (Cart) -> Unit) {
            binding.tvName.text = cart.product.name
            binding.tvCategory.text = cart.product.category
            binding.tvRating.text = cart.product.rating
            binding.tvQty.text = cart.qty.toString()
            binding.tvPrice.text = FormatRupiah.format(cart.product.price * cart.qty)

            Glide.with(binding.root.context)
                .load(cart.product.image)
                .placeholder(R.drawable.image_slider2)
                .error(R.drawable.image_slider3)
                .into(binding.imgImage)

            Log.d("imageProduct", cart.product.image)

            binding.btnMinus.setOnClickListener {
                if (cart.qty == 1) {
                    removeData(adapterPosition)
                }

                cart.qty -= 1
                binding.tvQty.text = cart.qty.toString()
                binding.tvPrice.text = FormatRupiah.format(cart.product.price * cart.qty)
                fragment.countTotal()
            }

            binding.btnPlus.setOnClickListener {
                cart.qty += 1
                binding.tvQty.text = cart.qty.toString()
                binding.tvPrice.text = FormatRupiah.format(cart.product.price * cart.qty)
                fragment.countTotal()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newData: MutableList<Cart>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        val item = list[position]
        list.removeAt(position)
        CartList.CardData.remove(item)
        notifyItemRemoved(position)
    }
}