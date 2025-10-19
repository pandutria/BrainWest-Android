package com.example.brainwest_android.ui.community.chating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Community
import com.example.brainwest_android.data.repository.CommunityRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.community.list.CommunityViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class ChatingCommunityViewModel(val repo: CommunityRepository): ViewModel() {
    private val _result = MutableLiveData<State<Community>>()
    val result: LiveData<State<Community>> get() = _result

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
}

class ChatingCommunityViewModelFactory(private val repo: CommunityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatingCommunityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatingCommunityViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}