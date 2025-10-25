package com.example.brainwest_android.ui.product.cart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.MidtransProductTransactionHeader
import com.example.brainwest_android.data.model.ProductTransactionDetail
import com.example.brainwest_android.data.model.ProductTransactionHeader
import com.example.brainwest_android.data.repository.ProductRepository
import com.example.brainwest_android.data.repository.ProductTransactionRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.product.detail.DetailProductViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class CartProductViewModel(val repo: ProductTransactionRepository): ViewModel() {
    private val _headerResult = MutableLiveData<State<MidtransProductTransactionHeader>>()
    val headerResult: LiveData<State<MidtransProductTransactionHeader>> get() = _headerResult

    private val _detailResult = MutableLiveData<State<ProductTransactionHeader>>()
    val detailResult: LiveData<State<ProductTransactionHeader>> get() = _detailResult

    fun postProductHeader(total: Int, address: String, context: Context) {
        viewModelScope.launch {
            _headerResult.postValue(State.Loading)
            try {
                val res = repo.postTransactionHeader(total, address, context)
                if (res.isSuccessful) _headerResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _headerResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _headerResult.postValue(State.Error(e.message!!))
            }
        }
    }

    fun postProductDetail(product_id: Int, header_id: Int, qty: Int) {
        viewModelScope.launch {
            _detailResult.postValue(State.Loading)
            try {
                val res = repo.postTransactionDetail(product_id, header_id, qty)
                if (res.isSuccessful) _detailResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _detailResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch(e: Exception) {
                _detailResult.postValue(State.Error(e.message!!))
            }
        }
    }

    fun deleteProductHeader(id: Int) {
        viewModelScope.launch {
            repo.deleteTransactionHeader(id)
        }
    }
}

class CartProductViewModelFactory(private val repo: ProductTransactionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}