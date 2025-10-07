package com.example.brainwest_android.ui.consultation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.ConsultationHistoryMessage
import com.example.brainwest_android.data.repository.ChatConsultationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class HistoryConsultationViewModel(private val repo: ChatConsultationRepository) : ViewModel() {
    private val _historyResult = MutableLiveData<State<List<ConsultationHistoryMessage>>>()
    val historyResult: LiveData<State<List<ConsultationHistoryMessage>>> = _historyResult

    fun getHistory() {
        viewModelScope.launch {
            _historyResult.postValue(State.Loading)
            try {
                val res = repo.getHistory()
                if (res.isSuccessful) _historyResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _historyResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _historyResult.postValue(State.Error(e.message.toString()))
            }
        }
    }
}

class HistoryConsultationViewModelFactory(private val repo: ChatConsultationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryConsultationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryConsultationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}