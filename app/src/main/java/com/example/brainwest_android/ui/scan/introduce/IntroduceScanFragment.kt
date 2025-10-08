package com.example.brainwest_android.ui.scan.introduce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentIntroduceScanBinding

class IntroduceScanFragment : Fragment() {
    lateinit var binding: FragmentIntroduceScanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIntroduceScanBinding.inflate(layoutInflater)

        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_introduceFragment_to_uploadFragment)
        }
        return binding.root
    }
}