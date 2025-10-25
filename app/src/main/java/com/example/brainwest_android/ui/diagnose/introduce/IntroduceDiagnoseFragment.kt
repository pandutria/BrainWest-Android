package com.example.brainwest_android.ui.diagnose.introduce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentIntroduceDiagnoseBinding

class IntroduceDiagnoseFragment : Fragment() {
    lateinit var binding: FragmentIntroduceDiagnoseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIntroduceDiagnoseBinding.inflate(layoutInflater)

        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_introduceFragment_to_questionFragment)
        }

        return binding.root
    }
}