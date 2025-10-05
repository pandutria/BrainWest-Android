package com.example.brainwest_android.ui.consultation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Doctor
import com.example.brainwest_android.data.repository.ConsultationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class ListConsultationViewModel(val repo: ConsultationRepository): ViewModel() {
    private val _getDoctorResult = MutableLiveData<State<List<Doctor>>>()
    val getDoctorResult: LiveData<State<List<Doctor>>> get() = _getDoctorResult

    fun getDoctor() {
        viewModelScope.launch {
            _getDoctorResult.postValue(State.Loading)
            try {
                val res = repo.getDoctor()

                if (res.isSuccessful) _getDoctorResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _getDoctorResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _getDoctorResult.postValue(State.Error(e.message!!))
            }
        }
    }
}

class ListConsultationViewModelFactory(private val repo: ConsultationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListConsultationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListConsultationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}