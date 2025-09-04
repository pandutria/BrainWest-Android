package com.example.brainwest_android.ui.event.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentTransactionEventBinding

class TransactionEventFragment : Fragment() {
    lateinit var binding: FragmentTransactionEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionEventBinding.inflate(layoutInflater)
        return binding.root
    }
}