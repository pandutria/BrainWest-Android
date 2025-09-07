package com.example.brainwest_android.ui.chatbot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brainwest_android.R
import com.example.brainwest_android.databinding.FragmentChatBotBinding

class ChatBotFragment : Fragment() {
    lateinit var binding: FragmentChatBotBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBotBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out)
        }

        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_chatBotFragment_to_chatingChatBotFragment)
        }


        return binding.root
    }
}