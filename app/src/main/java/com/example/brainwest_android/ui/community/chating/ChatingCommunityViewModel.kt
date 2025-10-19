package com.example.brainwest_android.ui.community.chating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Community
import com.example.brainwest_android.data.model.CommunityHistoryMessage
import com.example.brainwest_android.data.model.CommunityMessage
import com.example.brainwest_android.data.network.response.BaseResponse
import com.example.brainwest_android.data.repository.ChatCommunityRepository
import com.example.brainwest_android.data.repository.CommunityRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.community.list.CommunityViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class ChatingCommunityViewModel(val repo: CommunityRepository, val repo2: ChatCommunityRepository): ViewModel() {
    private val _result = MutableLiveData<State<Community>>()
    val result: LiveData<State<Community>> get() = _result

    private val _messages = MutableLiveData<List<CommunityMessage>>()
    val messages: LiveData<List<CommunityMessage>> = _messages

    private val _post = MutableLiveData<State<CommunityHistoryMessage>>()
    val post: LiveData<State<CommunityHistoryMessage>> get() = _post

    fun getCommunityById(id: Int) {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.getCommunityById(id)
                if (res.isSuccessful)
                    _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else
                    _result.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _result.postValue(State.Error(e.message!!))
            }
        }
    }

    fun sendMessageToApi(groupId: Int, message: String) {
        viewModelScope.launch {
            _post.postValue(State.Loading)
            try {
                val res = repo2.sendMessageToApi(message, groupId)
                if (res.isSuccessful) _post.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _post.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _post.postValue(State.Error(e.message!!))
            }
        }
    }

    fun startListening(groupId: Int) {
        repo2.listenMessages(groupId) { list ->
            _messages.postValue(list)
        }
    }

    fun sendMessage(groupId: Int, message: String) {
        repo2.sendMessage(groupId, message)
    }
}

class ChatingCommunityViewModelFactory(private val repo: CommunityRepository, private val repo2: ChatCommunityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatingCommunityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatingCommunityViewModel(repo, repo2) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}