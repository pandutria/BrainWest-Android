package com.example.brainwest_android.ui.community.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Community
import com.example.brainwest_android.data.model.CommunityMember
import com.example.brainwest_android.data.network.response.BaseResponse
import com.example.brainwest_android.data.repository.ChatConsultationRepository
import com.example.brainwest_android.data.repository.CommunityRepository
import com.example.brainwest_android.data.repository.ConsultationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.consultation.chating.ChatingConsultationViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.launch

class CommunityViewModel(val repo: CommunityRepository): ViewModel() {
    private val _result = MutableLiveData<State<List<Community>>>()
    val result: LiveData<State<List<Community>>> get() = _result

    private val _postResult = MutableLiveData<State<CommunityMember>>()
    val postResult: LiveData<State<CommunityMember>> get() = _postResult

    fun getCommunity() {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.getCommunity()
                if (res.isSuccessful) _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _result.postValue(State.Error(res.message()))
            } catch (e: Exception) {
                _result.postValue(State.Error(e.message.toString()))
            }
        }
    }

    fun postCommunityMember(context: Context, group_id: Int) {
        viewModelScope.launch {
            _postResult.postValue(State.Loading)
            try {
                val res = repo.postMemberCommunity(context, group_id)
                if (res.isSuccessful) _postResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _postResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _postResult.postValue(State.Error(e.message.toString()))
            }
        }
    }
}

class CommunityViewModelFactory(private val repo: CommunityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommunityViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
