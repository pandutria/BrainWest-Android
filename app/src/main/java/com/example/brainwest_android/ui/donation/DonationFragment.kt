package com.example.brainwest_android.ui.donation

import android.os.Bundle
import android.provider.Telephony.ServiceStateTable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.DonationRepository
import com.example.brainwest_android.databinding.FragmentDonationBinding
import com.example.brainwest_android.ui.adapter.DonationAdapter
import com.example.brainwest_android.utils.State

class DonationFragment : Fragment() {
    lateinit var binding: FragmentDonationBinding

    private val viewModel: DonationViewModel by viewModels {
        DonationViewModelFactory(DonationRepository())
    }

    lateinit var adapter: DonationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDonationBinding.inflate(layoutInflater)

        adapter = DonationAdapter {donation ->

        }

        showData()
        return binding.root
    }

    fun showData() {
        viewModel.getAllDonation(requireContext())
        viewModel.getAllDonationResult.observe(viewLifecycleOwner) {state ->
            when(state) {
                is State.Loading -> {

                }
                is State.Success -> {
                    adapter.setData(state.data)
                    binding.rvDonation.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvDonation.adapter = adapter
                }
                is State.Error -> {

                }
            }
        }
    }
}