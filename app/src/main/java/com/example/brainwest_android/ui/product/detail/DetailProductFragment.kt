package com.example.brainwest_android.ui.product.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.brainwest_android.R
import com.example.brainwest_android.data.local.CartList
import com.example.brainwest_android.data.model.Cart
import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.data.repository.ProductRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentDetailProductBinding
import com.example.brainwest_android.utils.FormatRupiah
import com.example.brainwest_android.utils.Helper

class DetailProductFragment : Fragment() {
    lateinit var binding: FragmentDetailProductBinding

    private val viewModel: DetailProductViewModel by viewModels {
        DetailProductViewModelFactory(ProductRepository())
    }

    var qty = 0
    var productId: Int = 0
    var name: String = ""
    var category: String = ""
    var rating: String = ""
    var price: Int = 0
    var description: String = ""
    var image: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailProductBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            val navController = findNavController()

            val fromMain = requireActivity().intent.getBooleanExtra("from_main", false)

            if (fromMain) {
                requireActivity().finish()
                requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
                return@setOnClickListener
            }

            val canPop = navController.popBackStack()

            if (!canPop) {
                requireActivity().finish()
                requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
            }
        }

        binding.tvQty.text = qty.toString()

        binding.btnMinus.setOnClickListener {
            if (qty == 0) return@setOnClickListener Helper.showErrorToast(requireContext(), "Jumlah minimal 1")
            qty -= 1
            binding.tvQty.text = qty.toString()
        }

        binding.btnPlus.setOnClickListener {
            qty += 1
            binding.tvQty.text = qty.toString()
        }

        binding.btnAddToCart.setOnClickListener {
            if (qty == 0)
                return@setOnClickListener Helper.showErrorToast(requireContext(), "Jumlah minimal 1")

            for (i in CartList.CardData) {
                if (i.product.id == productId) {
                    i.qty += qty
                    Helper.showSuccessToast(requireContext(), "Berhasil menambahkan jumlah")
                    return@setOnClickListener
                }
            }

            CartList.CardData.add(
                Cart(
                    qty,
                    Product(productId, name, category, rating, price, description, image)
                )
            )
            Helper.showSuccessToast(requireContext(), "Berhasil ditambahkan ke keranjang")
        }

        showData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            val navController = findNavController()

            val fromMain = requireActivity().intent.getBooleanExtra("from_main", false)

            if (fromMain) {
                requireActivity().finish()
                requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
                return@addCallback
            }

            val canPop = navController.popBackStack()

            if (!canPop) {
                requireActivity().finish()
                requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
            }
        }
    }

    fun showData() {
        val id = requireArguments().getInt("id", 0)
        viewModel.getProductById(id)
        viewModel.result.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.layoutContent.visibility = View.GONE
                    binding.layoutBtn.visibility = View.GONE
                }
                is State.Success -> {
                    productId = state.data.id
                    name = state.data.name
                    category = state.data.category
                    rating = state.data.rating
                    price = state.data.price
                    image = state.data.image

                    binding.tvName.text = state.data.name
                    binding.tvCategory.text = state.data.category
                    binding.tvPrice.text = FormatRupiah.format(state.data.price)
                    binding.tvDesc.text = state.data.description
                    binding.tvRating.text = state.data.rating

                    Glide.with(requireContext())
                        .load(state.data.image)
                        .into(binding.imgImage)


                    binding.pbLoading.visibility = View.GONE
                    binding.layoutContent.visibility = View.VISIBLE
                    binding.layoutBtn.visibility = View.VISIBLE
                }
                is State.Error -> {
                    Helper.showErrorToast(requireContext(), state.message)
                }
            }
        }
    }
}