package com.example.brainwest_android.ui.donation.detail

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.DonationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentDetailDonationBinding
import com.example.brainwest_android.utils.FormatRupiah
import com.example.brainwest_android.utils.Helper

class DetailDonationFragment : Fragment() {
    lateinit var binding: FragmentDetailDonationBinding

    private val viewModel: DetailDonationViewModel by viewModels {
        DetailDonationViewModelFactory(DonationRepository())
    }

    var donation_id = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailDonationBinding.inflate(layoutInflater)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.bg)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDonation.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("id", donation_id)
            }
            findNavController().navigate(R.id.action_detailDonationFragment_to_transactionFragment, bundle)
        }

        showData()
        return binding.root
    }

    fun showData() {
        val id = requireArguments().getInt("id")
        viewModel.getDonationById(requireContext(), id)
        viewModel.getDonationByIdResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.layoutContent.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.layoutContent.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE

                    donation_id = state.data.id!!
                    binding.tvTitle.text = state.data.title
                    binding.tvRestDay.text = state.data.deadline
                    binding.tvDesc.text = state.data.desc
                    binding.tvTargetDonate.text = FormatRupiah.format(state.data.target!!)
                    binding.tvCurrentDonate.text = FormatRupiah.format(state.data.current_donate!!)

                    Glide.with(binding.root.context)
                        .load(state.data.image)
                        .into(binding.imgImage)
                }
                is State.Error -> {
                    findNavController().popBackStack()
                    Helper.showSuccessToast(requireContext(), state.message)
                }
            }
        }
    }
}