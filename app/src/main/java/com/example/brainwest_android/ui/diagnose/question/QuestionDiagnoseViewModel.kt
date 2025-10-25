package com.example.brainwest_android.ui.diagnose.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Diagnose
import com.example.brainwest_android.data.repository.DiagnoseRepository
import com.example.brainwest_android.data.repository.ProductRepository
import com.example.brainwest_android.ui.home.HomeViewModel
import com.example.brainwest_android.utils.Helper
import kotlinx.coroutines.launch
import retrofit2.Response

class QuestionDiagnoseViewModel(val repo: DiagnoseRepository): ViewModel() {
    private val _result = MutableLiveData<Response<Diagnose>>()
    val result: LiveData<Response<Diagnose>> get() = _result

    fun diagnose(question: String, answer: String) {
        viewModelScope.launch {
            try {
                val res = repo.diagnose(question, answer)
                _result.postValue(res)
            } catch (e: Exception) {
                Helper.showErrorLog(e.message)
            }
        }
    }
}

class QuestionDiagnoseViewModeFactory(private val repo: DiagnoseRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionDiagnoseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionDiagnoseViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}