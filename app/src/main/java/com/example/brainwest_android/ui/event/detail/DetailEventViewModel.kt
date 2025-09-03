package com.example.brainwest_android.ui.event.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.data.repository.EventRepository
import com.example.brainwest_android.ui.event.EventViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.utils.State
import kotlinx.coroutines.launch

class DetailEventViewModel(private val repo: EventRepository): ViewModel() {
    private val _getEventByIdResult = MutableLiveData<State<Event>>()
    val getEventBydIdResult: LiveData<State<Event>> get() = _getEventByIdResult

    fun getEventById(context: Context, id: Int) {
        viewModelScope.launch {
            _getEventByIdResult.postValue(State.Loading)
            try {
                val res = repo.getEventById(context,id)

                if (res.isSuccessful) _getEventByIdResult.postValue(State.Success(res.body()!!.data, res.body()!!.message))
                else _getEventByIdResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                Helper.showErrorLog(e.message)
                _getEventByIdResult.postValue(State.Error(e.message!!))
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