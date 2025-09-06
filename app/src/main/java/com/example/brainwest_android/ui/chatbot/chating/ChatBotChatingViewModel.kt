package com.example.brainwest_android.ui.chatbot.chating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.network.response.gemini.GeminiChatMessage
import com.example.brainwest_android.data.repository.GeminiRepository
import kotlinx.coroutines.launch

class ChatBotChatingViewModel(private val repo: GeminiRepository): ViewModel() {
    private val _messages = MutableLiveData<List<GeminiChatMessage>>(emptyList())
    val messages: LiveData<List<GeminiChatMessage>> = _messages

    fun askGemini(apiKey: String, message: String) {
        val currentList = _messages.value?.toMutableList() ?: mutableListOf()
        currentList.add(GeminiChatMessage(message, true))
        _messages.value = currentList

        viewModelScope.launch {
            val reply = repo.sendMessage(apiKey, message) ?: "Error: gagal dapat jawaban"
            val updatedList = _messages.value?.toMutableList() ?: mutableListOf()
            updatedList.add(GeminiChatMessage(reply, false))
            _messages.postValue(updatedList)
        }
    }
}

class GeminiViewModelFactory(private val repo: GeminiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatBotChatingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatBotChatingViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}