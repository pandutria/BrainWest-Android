package com.example.brainwest_android.ui.event.me

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.EventTransaction
import com.example.brainwest_android.data.repository.EventRepository
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.data.state.State
import kotlinx.coroutines.launch

class MyTicketEventViewModel(private val repo: EventRepository): ViewModel() {
    private val _getMyEventResult = MutableLiveData<State<List<EventTransaction>>>()
    val getMyEventResult: LiveData<State<List<EventTransaction>>> get() = _getMyEventResult

    fun getMyEvent(context: Context) {
        viewModelScope.launch {
            _getMyEventResult.postValue(State.Loading)
            try {
                val res = repo.getMyEventTransaction(context)

                if (res.isSuccessful) _getMyEventResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _getMyEventResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                Helper.showErrorLog(e.message)
                _getMyEventResult.postValue(State.Error(e.message!!))
            }
        }
    }
}

class MyEventViewModelfactory(private val repo: EventRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyTicketEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyTicketEventViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}