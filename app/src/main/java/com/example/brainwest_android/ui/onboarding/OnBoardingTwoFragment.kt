package com.example.brainwest_android.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentOnBoardingTwoBinding

class OnBoardingTwoFragment : Fragment() {
    lateinit var binding: FragmentOnBoardingTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingTwoBinding.inflate(layoutInflater)

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingTwoFragment_to_onBoardingThreeFragment)
        }

        binding.btnPrev.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingTwoFragment_to_onBoardingOneFragment)
        }

        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingTwoFragment_to_onBoardingThreeFragment)
        }

        return binding.root
    }
}