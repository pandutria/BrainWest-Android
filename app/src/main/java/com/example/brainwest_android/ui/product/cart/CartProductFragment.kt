package com.example.brainwest_android.ui.product.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainwest_android.R
import com.example.brainwest_android.data.local.CartList
import com.example.brainwest_android.data.model.Cart
import com.example.brainwest_android.databinding.FragmentCartProductBinding
import com.example.brainwest_android.ui.adapter.CartAdapter
import com.example.brainwest_android.ui.adapter.ProductAdapter
import com.example.brainwest_android.utils.FormatRupiah
import com.example.brainwest_android.utils.Helper
import java.text.Normalizer.Form


class CartProductFragment : Fragment() {
   lateinit var binding: FragmentCartProductBinding

   lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartProductBinding.inflate(layoutInflater)

        adapter = CartAdapter(list = mutableListOf(), this, onClick = {cart ->

        })

        showData()
        countTotal()

        binding.btnDelete.setOnClickListener {
            if (CartList.CardData.count() == 0)
                return@setOnClickListener Helper.showErrorToast(requireContext(), "Keranjang masih kosong")
            CartList.CardData.removeAll(CartList.CardData)
            showData()
        }

        return binding.root
    }

    fun showData() {
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        adapter.setData(CartList.CardData)
        binding.rvCart.adapter = adapter
    }

    fun countTotal() {
        var subTotal = 0
        for (i in CartList.CardData) {
            subTotal += (i.product.price * i.qty)
            binding.tvSubTotal.text = FormatRupiah.format(subTotal)
        }
        val delivery = 10000
        binding.tvDelivery.text = FormatRupiah.format(delivery)
        binding.tvTotal.text = FormatRupiah.format(subTotal + delivery)
    }
}