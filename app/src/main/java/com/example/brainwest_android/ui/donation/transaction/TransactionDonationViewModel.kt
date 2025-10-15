package com.example.brainwest_android.ui.donation.transaction

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.MidtransDonationTransaction
import com.example.brainwest_android.data.repository.DonationRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.donation.history.HistoryTransactionDonationViewModel
import kotlinx.coroutines.launch

class TransactionDonationViewModel(val repo: DonationRepository): ViewModel() {
    private val _result = MutableLiveData<State<MidtransDonationTransaction>>()
    val result: LiveData<State<MidtransDonationTransaction>> get() = _result

    fun postDonateTransaction(context: Context, donation_id: Int, total_donate: Int) {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.postDonateTransaction(context, donation_id, total_donate)

                if (res.isSuccessful) _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _result.postValue(State.Error(res.message()))
            } catch (e: Exception) {
                _result.postValue(State.Error(e.message.toString()))
            }
        }
    }
}

class TransactionDonationViewModelFactory(private val repo: DonationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionDonationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionDonationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}