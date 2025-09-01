package com.example.brainwest_android.ui.education.detail.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.data.repository.AuthRepository
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.ui.auth.login.LoginViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.State
import kotlinx.coroutines.launch

class ArticleViewModel(private val repo: EducationRepository): ViewModel() {

}

class ArticleViewModelFactory(private val repo: EducationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}