package com.example.brainwest_android.ui.community.history

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.CommunityHistory
import com.example.brainwest_android.data.repository.ChatCommunityRepository
import com.example.brainwest_android.data.repository.CommunityRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.community.chating.ChatingCommunityViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class HistoryCommunityViewModel(val repo: CommunityRepository): ViewModel() {
    private val _result = MutableLiveData<State<List<CommunityHistory>>>()
    val result: MutableLiveData<State<List<CommunityHistory>>> get() = _result

    fun getHistoryMessage(context: Context) {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.getHistoryCommunity(context)
                if (res.isSuccessful) _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _result.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _result.postValue(State.Error(e.message.toString()))
            }
        }
    }
}

class HistoryCommunityViewModelFactory(private val repo: CommunityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryCommunityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryCommunityViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}