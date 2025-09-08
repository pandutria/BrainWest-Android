package com.example.brainwest_android.ui.donation.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentPaymentDonationBinding

class PaymentDonationFragment : Fragment() {
    lateinit var binding: FragmentPaymentDonationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentDonationBinding.inflate(layoutInflater)
        return binding.root
    }
}