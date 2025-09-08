package com.example.brainwest_android.ui.event.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.data.model.MidtransEventTransaction
import com.example.brainwest_android.data.repository.EventRepository
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.data.state.State
import kotlinx.coroutines.launch

class DetailEventViewModel(private val repo: EventRepository): ViewModel() {
    private val _getEventByIdResult = MutableLiveData<State<Event>>()
    val getEventBydIdResult: LiveData<State<Event>> get() = _getEventByIdResult

    private val _postEventTransactionResult = MutableLiveData<State<MidtransEventTransaction>>()
    val postEventTransactionResult: LiveData<State<MidtransEventTransaction>> get() = _postEventTransactionResult

    fun getEventById(context: Context, id: Int) {
        viewModelScope.launch {
            _getEventByIdResult.postValue(State.Loading)
            try {
                val res = repo.getEventById(context,id)

                if (res.isSuccessful) _getEventByIdResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _getEventByIdResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                Helper.showErrorLog(e.message)
                _getEventByIdResult.postValue(State.Error(e.message!!))
            }
        }
    }

    fun postEventTransaction(context: Context, id: Int) {
        viewModelScope.launch {
            _postEventTransactionResult.postValue(State.Loading)
            try {
                val res = repo.postEventTransaction(context, id)

                if (res.isSuccessful) _postEventTransactionResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _postEventTransactionResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                Helper.showErrorLog(e.message)
                _postEventTransactionResult.postValue(State.Error(e.message!!))
            }
        }
    }
}

class DetailEventViewModelfactory(private val repo: EventRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailEventViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}