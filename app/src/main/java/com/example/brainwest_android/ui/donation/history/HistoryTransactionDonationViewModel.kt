package com.example.brainwest_android.ui.donation.history

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Donation
import com.example.brainwest_android.data.model.DonationTransaction
import com.example.brainwest_android.data.repository.DonationRepository
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.education.EducationViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class HistoryTransactionDonationViewModel(val repo: DonationRepository): ViewModel() {
    private val _result = MutableLiveData<State<List<DonationTransaction>>>()
    val result: LiveData<State<List<DonationTransaction>>> get() = _result

    fun getHistoryDonation(context: Context) {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.getHistoryDonation(context)

                res.body()?.let {
                    Log.d("RAW_JSON", it.toString())
                }

                if (res.isSuccessful) _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _result.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _result.postValue(State.Error(e.message!!))
            }
        }
    }
}

class HistoryTransactionDonationViewModelFactory(private val repo: DonationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryTransactionDonationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryTransactionDonationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}