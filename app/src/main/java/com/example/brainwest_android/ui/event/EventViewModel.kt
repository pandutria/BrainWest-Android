package com.example.brainwest_android.ui.event

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.data.repository.EventRepository
import com.example.brainwest_android.ui.education.EducationViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.utils.State
import kotlinx.coroutines.launch

class EventViewModel(private val repo: EventRepository): ViewModel() {
    private val _getAllEventResult = MutableLiveData<State<List<Event>>>()
    val getAllEventResult: LiveData<State<List<Event>>> get() = _getAllEventResult

    fun getAllEvent(context: Context) {
        viewModelScope.launch {
            _getAllEventResult.postValue(State.Loading)
            try {
                val res = repo.getAllEvent(context)

                if (res.isSuccessful) _getAllEventResult.postValue(State.Success(res.body()!!.data, res.body()!!.message))
                else _getAllEventResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                Helper.showErrorLog(e.message)
                _getAllEventResult.postValue(State.Error(e.message!!))
            }
        }
    }
}

class EventViewModelfactory(private val repo: EventRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}