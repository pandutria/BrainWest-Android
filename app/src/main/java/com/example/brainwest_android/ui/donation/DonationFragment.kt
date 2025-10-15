package com.example.brainwest_android.ui.donation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainwest_android.R
import com.example.brainwest_android.data.local.MyWalletPref
import com.example.brainwest_android.data.repository.DonationRepository
import com.example.brainwest_android.databinding.FragmentDonationBinding
import com.example.brainwest_android.ui.adapter.DonationAdapter
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.utils.FormatRupiah

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

        binding.header.setBackgroundColor(Color.TRANSPARENT)
        requireActivity().window.statusBarColor = Color.parseColor("#0059D0")

        binding.tvWallet.text = FormatRupiah.format(MyWalletPref(requireContext()).getWallet())

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        adapter = DonationAdapter {donation ->
            val bundle = Bundle().apply {
                putInt("id", donation.id!!)
            }
            findNavController().navigate(R.id.action_donationFragment_to_detailDonationFragment, bundle)
        }

        showData()
        setupAppBar()
        return binding.root
    }

    fun setupAppBar() {
        binding.scroll.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > 0) {
                binding.header.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg))
                requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.bg)
            } else {
                binding.header.setBackgroundColor(Color.TRANSPARENT)
                requireActivity().window.statusBarColor = Color.parseColor("#0059D0")
            }
        }
    }


    fun showData() {
        viewModel.getAllDonation(requireContext())
        viewModel.getAllDonationResult.observe(viewLifecycleOwner) {state ->
            when(state) {
                is State.Loading -> {
                    binding.rvDonation.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.rvDonation.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
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