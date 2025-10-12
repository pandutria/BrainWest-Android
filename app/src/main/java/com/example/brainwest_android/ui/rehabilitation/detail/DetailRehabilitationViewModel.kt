package com.example.brainwest_android.ui.rehabilitation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.RehabilitationVideo
import com.example.brainwest_android.data.repository.RehabilitationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.rehabilitation.input.InputRehabilitationViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class DetailRehabilitationViewModel(val repo: RehabilitationRepository): ViewModel() {
    private val _result = MutableLiveData<State<RehabilitationVideo>>()
    val result: MutableLiveData<State<RehabilitationVideo>> get() = _result

    fun getVideoRehabById(id: Int) {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.getVideoRehabById(id)
                if (res.isSuccessful) _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                 else _result.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _result.postValue(State.Error(e.message.toString()))
            }
        }
    }
}

class DetailRehabilitationViewModelFactory(private val repo: RehabilitationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailRehabilitationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailRehabilitationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}