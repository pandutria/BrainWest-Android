package com.example.brainwest_android.ui.product.list

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.data.repository.ProductRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentListProductBinding
import com.example.brainwest_android.ui.adapter.ProductAdapter
import com.example.brainwest_android.ui.parent.ProductActivity
import com.example.brainwest_android.utils.Helper

class ListProductFragment : Fragment() {
    lateinit var binding: FragmentListProductBinding

    private val viewModel: ListProductViewModel by viewModels {
        ListProductViewModelFactory(ProductRepository())
    }

    lateinit var adapter: ProductAdapter

    var list: List<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListProductBinding.inflate(layoutInflater)

        binding.root.setOnClickListener {
            Helper.clearFocusOnEdtText(requireContext(), binding.etSearch)
        }

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        adapter = ProductAdapter {product ->
            val bundle = Bundle().apply {
                putInt("id", product.id)
            }

            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }

        binding.btnCart.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_cartFragment)
        }

        binding.btnOrder.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_historyFragment)
        }

        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val query = p0.toString().trim().lowercase()
                if (query.isNotEmpty()) adapter.setData(list.filter { x -> x.name.lowercase().contains(query) })
                else adapter.setData(list)
            }
        })

        showData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }
    }

    fun showData() {
        viewModel.getProduct()
        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.rvProduct.visibility = View.GONE
                }
                is State.Success -> {
                    adapter.setPage("!home")
                    list = state.data
                    adapter.setData(list)
                    binding.rvProduct.adapter = adapter
                    binding.pbLoading.visibility = View.GONE
                    binding.rvProduct.visibility = View.VISIBLE
                }
                is State.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.rvProduct.visibility = View.VISIBLE
                    viewModel.getProduct()
                }
            }
        }
    }
}