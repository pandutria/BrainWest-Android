package com.example.brainwest_android.ui.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentOnBoardingThreeBinding


class OnBoardingThreeFragment : Fragment() {
    lateinit var binding: FragmentOnBoardingThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingThreeBinding.inflate(layoutInflater)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingThreeFragment_to_loginFragment)
        }

        return binding.root
    }


}