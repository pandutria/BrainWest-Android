package com.example.brainwest_android.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.ui.education.EducationViewModel

class HomeViewModel:ViewModel() {
    val scrollY = MutableLiveData<Int>()
}

class HomeViewModelfactory() :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}