package com.example.brainwest_android.ui.chatbot.chating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.example.brainwest_android.R
import com.example.brainwest_android.data.network.api.gemini.GeminiRetrofitIntance
import com.example.brainwest_android.data.repository.GeminiRepository
import com.example.brainwest_android.databinding.FragmentChatbotChatingBinding
import com.example.brainwest_android.ui.adapter.ChatBotAdapter


class ChatbotChatingFragment : Fragment() {
    lateinit var binding: FragmentChatbotChatingBinding
    private lateinit var adapter: ChatBotAdapter

    private val viewModel: ChatBotChatingViewModel by viewModels {
        GeminiViewModelFactory(GeminiRepository(GeminiRetrofitIntance.geminiApi))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatbotChatingBinding.inflate(layoutInflater)

        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        adapter = ChatBotAdapter(mutableListOf())
        binding.rvChat.adapter = adapter

        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            if (message.isNotBlank()) {
                viewModel.askGemini("AIzaSyDqD7S1HKYrwCv4ddpPsyKqw7JmXoU91x8", message)
                binding.etMessage.text.clear()
            }
        }

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.addMessage(messages)
            binding.rvChat.scrollToPosition(messages.size - 1)
        }

        return binding.root
    }
}