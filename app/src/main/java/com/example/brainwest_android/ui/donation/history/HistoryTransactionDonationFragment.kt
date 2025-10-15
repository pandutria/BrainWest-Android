package com.example.brainwest_android.ui.donation.history

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.DonationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentHistoryTransactionDonationBinding
import com.example.brainwest_android.databinding.ItemHistoryTransactionDonationBinding
import com.example.brainwest_android.ui.adapter.HistoryTransactionDonationAdapter
import com.example.brainwest_android.utils.Helper

class HistoryTransactionDonationFragment : Fragment() {
    lateinit var binding: FragmentHistoryTransactionDonationBinding

    private val viewModel: HistoryTransactionDonationViewModel by viewModels {
        HistoryTransactionDonationViewModelFactory(DonationRepository())
    }

    lateinit var adapter: HistoryTransactionDonationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryTransactionDonationBinding.inflate(inflater, container, false)

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.bg)

        adapter = HistoryTransactionDonationAdapter {hsitroy ->

        }

        showData()

        return binding.root
    }

    fun showData() {
        viewModel.getHistoryDonation(requireContext())
        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    binding.rvDonation.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    adapter.newData(state.data)
                    binding.rvDonation.adapter = adapter
                    binding.rvDonation.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                }
                is State.Error -> {
                    Helper.showErrorToast(requireContext(), "Terjadi kesealahan, tolong coba kembali")
                    Helper.showErrorLog(state.message)
                }
            }
        }
    }
}