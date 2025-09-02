package com.example.brainwest_android.ui.education.detail.video

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.ui.education.detail.article.ArticleViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.Helper
import com.example.brainwest_android.utils.State
import kotlinx.coroutines.launch

class VideoViewModel(private val repo: EducationRepository): ViewModel() {
    private val _getEducationByIdResult = MutableLiveData<State<Education>>()
    val getEducationByIdResult: LiveData<State<Education>> get() = _getEducationByIdResult

    fun getEducationById(context: Context, id: Int) {
        viewModelScope.launch {
            _getEducationByIdResult.postValue(State.Loading)
            try {
                val res = repo.getEducationById(context, id)
                if (res.isSuccessful) _getEducationByIdResult.postValue(State.Success(res.body()!!.data, res.message()))
                else _getEducationByIdResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                Helper.showErrorLog(e.message!!)
                _getEducationByIdResult.postValue(State.Error(e.message!!))
            }
        }
    }
}

class VideoViewModelFactory(private val repo: EducationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VideoViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}