package com.example.brainwest_android.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentOnBoardingOneBinding

class OnBoardingOneFragment : Fragment() {
    lateinit var binding: FragmentOnBoardingOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingOneBinding.inflate(layoutInflater)

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingOneFragment_to_onBoardingTwoFragment)
        }

        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingOneFragment_to_onBoardingThreeFragment)
        }

        return binding.root
    }


}