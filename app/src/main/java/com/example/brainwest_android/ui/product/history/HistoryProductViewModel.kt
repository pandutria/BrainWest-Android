package com.example.brainwest_android.ui.product.history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.ProductTransactionHeader
import com.example.brainwest_android.data.repository.ProductRepository
import com.example.brainwest_android.data.repository.ProductTransactionRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.product.detail.DetailProductViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class HistoryProductViewModel(val repo: ProductTransactionRepository): ViewModel() {
    private val _headerResult = MutableLiveData<State<List<ProductTransactionHeader>>>()
    val headerResult: LiveData<State<List<ProductTransactionHeader>>> get() = _headerResult

    fun getHeader(context: Context) {
        viewModelScope.launch {
            _headerResult.postValue(State.Loading)
            try {
                val res = repo.getTransactionHeaderByUser(context)
                if (res.isSuccessful) _headerResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _headerResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _headerResult.postValue(State.Error(e.message!!))
            }
        }
    }
}

class HistoryProductViewModelFactory(private val repo: ProductTransactionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}