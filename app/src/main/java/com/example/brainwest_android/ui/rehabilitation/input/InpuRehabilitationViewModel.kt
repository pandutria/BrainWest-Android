package com.example.brainwest_android.ui.rehabilitation.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Rehabilitation
import com.example.brainwest_android.data.model.RehabilitationVideo
import com.example.brainwest_android.data.repository.EventRepository
import com.example.brainwest_android.data.repository.RehabilitationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.event.EventViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class InputRehabilitationViewModel(val repo: RehabilitationRepository): ViewModel() {
    private val _result = MutableLiveData<State<List<RehabilitationVideo>>>()
    val result: LiveData<State<List<RehabilitationVideo>>> get() = _result

    fun getVideoByRehab(age: String, gender: String, medical_status: String, time_of_diagnosis: String) {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.getVideoByRehab(age, gender, medical_status, time_of_diagnosis)
                if (res.isSuccessful) _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _result.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _result.postValue(State.Error(e.message!!))
            }
        }
    }
}

class InputRehabilitationViewModelFactory(private val repo: RehabilitationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputRehabilitationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InputRehabilitationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

