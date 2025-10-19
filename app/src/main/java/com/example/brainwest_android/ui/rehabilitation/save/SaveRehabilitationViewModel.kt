package com.example.brainwest_android.ui.rehabilitation.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.RehabilitationVideo
import com.example.brainwest_android.data.repository.RehabilitationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.rehabilitation.list.ListRehabilitationFragment
import kotlinx.coroutines.launch

class SaveRehabilitationViewModel(val repo: RehabilitationRepository): ViewModel()  {
    private val _result = MutableLiveData<State<List<RehabilitationVideo>>>()
    val result: LiveData<State<List<RehabilitationVideo>>> get() = _result

    fun send(id: Int) {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.getRehabById(id)
                if (res.isSuccessful) _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _result.postValue(State.Error(res.message()))
            } catch (e: Exception) {
                _result.postValue(State.Error(e.message!!))
            }
        }
    }
}

class SaveRehabilitationViewModelFactory(private val repo: RehabilitationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaveRehabilitationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SaveRehabilitationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


