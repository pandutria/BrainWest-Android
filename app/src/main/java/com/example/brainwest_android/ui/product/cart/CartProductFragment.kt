package com.example.brainwest_android.ui.product.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainwest_android.R
import com.example.brainwest_android.data.local.CartList
import com.example.brainwest_android.data.model.Cart
import com.example.brainwest_android.data.repository.ProductTransactionRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentCartProductBinding
import com.example.brainwest_android.ui.adapter.CartAdapter
import com.example.brainwest_android.ui.adapter.ProductAdapter
import com.example.brainwest_android.ui.parent.ProductActivity
import com.example.brainwest_android.utils.FormatRupiah
import com.example.brainwest_android.utils.Helper
import kotlinx.coroutines.launch
import java.text.Normalizer.Form


class CartProductFragment : Fragment() {
   lateinit var binding: FragmentCartProductBinding

   private val viewModel: CartProductViewModel by viewModels {
       CartProductViewModelFactory(ProductTransactionRepository())
   }

   lateinit var adapter: CartAdapter

   var snap_token = ""
   var total = 0
    var headerId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartProductBinding.inflate(layoutInflater)

        viewModel.detailResult.observe(viewLifecycleOwner) {secondSatate ->
            when (secondSatate) {
                is State.Loading -> {
                    binding.pbLoadingBtnCheckout.visibility = View.VISIBLE
                    binding.btnCheckout.visibility = View.GONE
                }
                is State.Success -> {
                    binding.pbLoadingBtnCheckout.visibility = View.GONE
                    binding.btnCheckout.visibility = View.VISIBLE
//                    Helper.showSuccessToast(requireContext(), "Berhasil membeli produk")
                    val bundle = Bundle().apply {
                        putString("snap_token", snap_token)
                    }
                    findNavController().navigate(R.id.action_cartFragment_to_paymentFragment, bundle)
                    CartList.CardData.clear()
                    binding.etAddress.text.clear()
                    showData()
                }
                is State.Error -> {
                    binding.pbLoadingBtnCheckout.visibility = View.GONE
                    binding.btnCheckout.visibility = View.VISIBLE
                    if (headerId != 0) {
                        viewModel.deleteProductHeader(headerId)
                        Log.e("CartProductFragment", "Deleting header ID: $headerId because detail failed")
                    } else {
                        Log.e("CartProductFragment", "Header ID is 0 — cannot delete")
                    }

                    Helper.showErrorToast(requireContext(), secondSatate.message)
                    Helper.showErrorToast(requireContext(), secondSatate.message)
                }
            }
        }

        viewModel.headerResult.observe(viewLifecycleOwner) {state ->
            when(state) {
                is State.Loading -> {
                    binding.pbLoadingBtnCheckout.visibility = View.VISIBLE
                    binding.btnCheckout.visibility = View.GONE
                }
                is State.Success -> {
                    headerId = state.data.transaction_header.id
                    snap_token = state.data.snap_token

                    viewLifecycleOwner.lifecycleScope.launch {
                        try {
                            for (item in CartList.CardData) {
                                viewModel.postProductDetail(item.product.id, headerId, item.qty)
                            }
                        } catch (e: Exception) {
                            viewModel.deleteProductHeader(headerId)
                            Helper.showErrorToast(requireContext(), "Gagal kirim detail, header dihapus.")
                        }
                    }
                }
                is State.Error -> {
                    binding.pbLoadingBtnCheckout.visibility = View.GONE
                    binding.btnCheckout.visibility = View.VISIBLE
                    viewModel.deleteProductHeader(headerId)
                    Helper.showErrorToast(requireContext(), state.message)
                }
            }
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(requireActivity(), ProductActivity::class.java)
            intent.putExtra("from", "list")
            startActivity(intent)
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        adapter = CartAdapter(list = mutableListOf(), this, onClick = {cart ->

        })

        showData()
        countTotal()

        binding.btnDelete.setOnClickListener {
            if (CartList.CardData.count() == 0)
                return@setOnClickListener Helper.showErrorToast(requireContext(), "Keranjang masih kosong")
            CartList.CardData.clear()
            showData()
        }

        binding.btnCheckout.setOnClickListener {
            if (CartList.CardData.count() == 0)
                return@setOnClickListener Helper.showErrorToast(requireContext(), "Minimal 1 produk dikeranjang")
            if (binding.etAddress.text.toString() == "")
                return@setOnClickListener Helper.showErrorToast(requireContext(), "Semua input wajib diisi")
            sendToApi()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            val intent = Intent(requireActivity(), ProductActivity::class.java)
            intent.putExtra("from", "list")
            startActivity(intent)
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }
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
        total = (subTotal + delivery)
    }

    fun sendToApi() {
        viewModel.postProductHeader(total, binding.etAddress.text.toString(), requireContext())
    }
}