package com.example.brainwest_android.ui.consultation.chating

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.ConsultationHistoryMessage
import com.example.brainwest_android.data.model.ConsultationMessage
import com.example.brainwest_android.data.model.Doctor
import com.example.brainwest_android.data.repository.ChatConsultationRepository
import com.example.brainwest_android.data.repository.ConsultationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class ChatingConsultationViewModel(val repo: ConsultationRepository, val repoChat: ChatConsultationRepository) : ViewModel() {
    private val _getByIdResult = MutableLiveData<State<Doctor>>()
    val getByIdResult: LiveData<State<Doctor>> get() = _getByIdResult

    private val _messages = MutableLiveData<List<ConsultationMessage>>()
    val messages: LiveData<List<ConsultationMessage>> get() = _messages

    private val _sendResult = MutableLiveData<State<List<ConsultationHistoryMessage>>>()
    val sendResult: LiveData<State<List<ConsultationHistoryMessage>>> get() = _sendResult

    fun getDoctorById(id: Int) {
        viewModelScope.launch {
            _getByIdResult.postValue(State.Loading)
            try {
                val res = repo.getDoctorById(id)

                if (res.isSuccessful) _getByIdResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _getByIdResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _getByIdResult.postValue(State.Error(e.message!!))
            }
        }
    }

    fun startListening(userId: Int, doctorId: Int) {
        repoChat.listenMessages(userId, doctorId) { msgs ->
            _messages.postValue(msgs)
        }
    }

    fun sendMessage(userId: Int, doctorId: Int, text: String) {
        repoChat.sendMessage(userId, doctorId, text)
    }

    fun sendMessageToAPI(doctorId: Int, message: String) {
        viewModelScope.launch {
            _sendResult.postValue(State.Loading)
            try {
                val res = repoChat.sendMessageToAPI(doctorId, message)
                if (res.isSuccessful) _sendResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _sendResult.postValue(State.Error(ApiErrorHandler.parseError(res)))

            } catch (e: Exception) {
                _sendResult.postValue(State.Error(e.message!!))
            }
        }
    }
}

class ChatingConsultationViewModelFactory(private val repo: ConsultationRepository, private val repoChat: ChatConsultationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatingConsultationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatingConsultationViewModel(repo, repoChat) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}