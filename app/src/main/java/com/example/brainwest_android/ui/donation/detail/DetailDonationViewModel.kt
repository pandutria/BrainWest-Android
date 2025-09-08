package com.example.brainwest_android.ui.donation.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Donation
import com.example.brainwest_android.data.repository.DonationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.donation.DonationViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import com.example.brainwest_android.utils.Helper
import kotlinx.coroutines.launch

class DetailDonationViewModel(private val repo: DonationRepository): ViewModel() {
    private val _getDonationByIdResult = MutableLiveData<State<Donation>>()
    val getDonationByIdResult: LiveData<State<Donation>> get() = _getDonationByIdResult

    fun getDonationById(context: Context, id: Int) {
        viewModelScope.launch {
            _getDonationByIdResult.postValue(State.Loading)
            try {
                val res = repo.getDonationById(context,id)

                if (res.isSuccessful) _getDonationByIdResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _getDonationByIdResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _getDonationByIdResult.postValue(State.Error(e.message!!))
                Helper.showErrorLog(e.message)
            }
        }
    }
}

class DetailDonationViewModelFactory(private val repo: DonationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailDonationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailDonationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}