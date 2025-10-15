package com.example.brainwest_android.ui.donation.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.data.local.MyWalletPref
import com.example.brainwest_android.data.repository.DonationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentTransactionDonationBinding
import com.example.brainwest_android.utils.FormatRupiah
import com.example.brainwest_android.utils.Helper

class TransactionDonationFragment : Fragment() {
    lateinit var binding: FragmentTransactionDonationBinding

    private val viewModel: TransactionDonationViewModel by viewModels {
        TransactionDonationViewModelFactory(DonationRepository())
    }

    var nominal = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionDonationBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvWallet.text = FormatRupiah.format(MyWalletPref(requireContext()).getWallet())
        clickCard()

        binding.etNominal.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nominal = binding.etNominal.text.toString().replace("Rp ", "").replace(".", "").toInt()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.btnDonation.setOnClickListener {
            if (nominal == 0) return@setOnClickListener Helper.showSuccessToast(requireContext(), "Tolong isi nominal")
            sendTransaction()
        }

        return binding.root
    }

    fun sendTransaction() {
        val id = requireArguments().getInt("id")
        viewModel.postDonateTransaction(requireContext(), id, nominal)
        viewModel.result.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.btnDonation.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is State.Success -> {
                    binding.btnDonation.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                    MyWalletPref(requireContext()).minusWallet(nominal)
                    binding.tvWallet.text = FormatRupiah.format(MyWalletPref(requireContext()).getWallet())
                    val bundle = Bundle().apply {
                        putString("snap_token", state.data.snap_token)
                    }
                    findNavController().navigate(R.id.action_transactionFragment_to_paymentFragment, bundle)
                }
                is State.Error -> {
                    binding.btnDonation.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                    Helper.showErrorToast(requireContext(), state.message)
                    findNavController().popBackStack()
                }
            }
        }
    }

    fun clickCard() {
        binding.layout25k.setOnClickListener {
            binding.etNominal.setText(FormatRupiah.format(25000))
        }
        binding.layout65k.setOnClickListener {
            binding.etNominal.setText(FormatRupiah.format(65000))
        }
        binding.layout75k.setOnClickListener {
            binding.etNominal.setText(FormatRupiah.format(75000))
        }
    }
}