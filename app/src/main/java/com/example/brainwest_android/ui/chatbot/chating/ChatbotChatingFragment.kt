package com.example.brainwest_android.ui.chatbot.chating

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.R
import com.example.brainwest_android.data.network.api.gemini.GeminiRetrofitIntance
import com.example.brainwest_android.data.network.response.gemini.GeminiChatMessage
import com.example.brainwest_android.data.repository.GeminiRepository
import com.example.brainwest_android.databinding.FragmentChatbotChatingBinding
import com.example.brainwest_android.ui.adapter.ChatBotAdapter


class ChatbotChatingFragment : Fragment() {
    lateinit var binding: FragmentChatbotChatingBinding
    private lateinit var adapter: ChatBotAdapter

    private val viewModel: ChatBotChatingViewModel by viewModels {
        GeminiViewModelFactory(GeminiRepository(GeminiRetrofitIntance.geminiApi))
    }

    private val REQUEST_CODE_SPEECH_INPUT = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatbotChatingBinding.inflate(layoutInflater)

        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnMicrophone.setOnClickListener {
            startSpeechToText()
        }

        adapter = ChatBotAdapter(mutableListOf())
        binding.rvChat.adapter = adapter

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.rvChat.scrollToPosition(adapter.itemCount - 1)
            }
        })

        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            if (message.isNotBlank()) {
                val userMsg = GeminiChatMessage(message = message, isUser = true)
                adapter.addMessage(userMsg)
                binding.rvChat.scrollToPosition(adapter.itemCount - 1)
                viewModel.askGemini("AIzaSyDqD7S1HKYrwCv4ddpPsyKqw7JmXoU91x8", message)
                binding.etMessage.text.clear()
                binding.etMessage.clearFocus()
                val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                imm.hideSoftInputFromWindow(binding.etMessage.windowToken, 0)
            }
        }

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            val lastMessage = messages.lastOrNull()
            if (lastMessage != null && !lastMessage.isUser) {
                showTypingEffect(lastMessage)
            }
        }


        return binding.root
    }

    private fun showTypingEffect(fullMessage: GeminiChatMessage) {
        var index = 0
        val handler = Handler(Looper.getMainLooper())

        val emptyMsg = GeminiChatMessage(message = "", isUser = false)
        adapter.addMessage(emptyMsg)
        val position = adapter.itemCount - 1

        val runnable = object : Runnable {
            override fun run() {
                if (index <= fullMessage.message.length) {
                    val currentText = fullMessage.message.substring(0, index)
                    adapter.updateMessage(position, currentText)
                    binding.rvChat.scrollToPosition(position)
                    index++
                    handler.postDelayed(this, 100)
                }
            }
        }
        handler.post(runnable)
    }


    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID")
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Silakan bicara...")

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!result.isNullOrEmpty()) {
                binding.etMessage.setText(result[0])
            }
        }
    }
}