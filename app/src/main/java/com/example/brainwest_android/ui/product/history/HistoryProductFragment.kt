package com.example.brainwest_android.ui.product.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.ProductTransactionRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentHistoryProductBinding
import com.example.brainwest_android.ui.adapter.HistoryTransactionHeaderProductAdapter


class HistoryProductFragment : Fragment() {
    lateinit var binding: FragmentHistoryProductBinding

    private val viewModel: HistoryProductViewModel by viewModels {
        HistoryProductViewModelFactory(ProductTransactionRepository())
    }

    lateinit var adapter: HistoryTransactionHeaderProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryProductBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        adapter = HistoryTransactionHeaderProductAdapter {header ->

        }

        viewModel.getHeader(requireContext())
        viewModel.headerResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.rvHeader.visibility = View.GONE
                }
                is State.Success -> {
                    adapter.setData(state.data)
                    binding.rvHeader.adapter = adapter
                    binding.pbLoading.visibility = View.GONE
                    binding.rvHeader.visibility = View.VISIBLE
                }
                is State.Error -> {
                    findNavController().popBackStack()
                }
            }
        }

        return binding.root
    }
}