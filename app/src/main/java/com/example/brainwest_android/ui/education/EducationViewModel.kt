package com.example.brainwest_android.ui.education

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.data.state.State
import kotlinx.coroutines.launch

class EducationViewModel(private val repo: EducationRepository): ViewModel() {
    private val _getAllEducationResult = MutableLiveData<State<List<Education>>>()
    val getAllEducationResult: LiveData<State<List<Education>>> get() = _getAllEducationResult

    val scrollY = MutableLiveData<Int>()
    var articleRvState: Parcelable? = null
    var videoRvState: Parcelable? = null

    fun getAllArticle(context: Context) {
        viewModelScope.launch {
            _getAllEducationResult.postValue(State.Loading)
            try {
                val res = repo.getAllEducation(context)

                if (res.isSuccessful) _getAllEducationResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _getAllEducationResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                Helper.showErrorLog(e.message!!)
                _getAllEducationResult.postValue(State.Error(e.message!!))
            }
        }
    }
}

class EducationViewModelFactory(private val repo: EducationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EducationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EducationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}