package com.example.brainwest_android.ui.donation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Donation
import com.example.brainwest_android.data.repository.DonationRepository
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.State
import kotlinx.coroutines.launch

class DonationViewModel(private val repo: DonationRepository): ViewModel() {
    private val _getAllDonationResult = MutableLiveData<State<List<Donation>>>()
    val getAllDonationResult: LiveData<State<List<Donation>>> get() = _getAllDonationResult

    fun getAllDonation(context: Context) {
        viewModelScope.launch {
            _getAllDonationResult.postValue(State.Loading)
            try {
                val res = repo.getAllDonation(context)

                if (res.isSuccessful) _getAllDonationResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _getAllDonationResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _getAllDonationResult.postValue(State.Error(e.message!!))
            }
        }
    }
}

class DonationViewModelFactory(private val repo: DonationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}