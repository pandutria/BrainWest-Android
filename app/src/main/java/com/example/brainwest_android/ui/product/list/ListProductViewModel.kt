package com.example.brainwest_android.ui.product.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.data.repository.ProductRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.home.HomeViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class ListProductViewModel(val repo: ProductRepository): ViewModel() {
    private val _result = MutableLiveData<State<List<Product>>>()
    val result: MutableLiveData<State<List<Product>>> get() = _result

    fun getProduct() {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.getProduct()
                if (res.isSuccessful) _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _result.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _result.postValue(State.Error(e.message.toString()))
            }
        }
    }
}

class ListProductViewModelFactory(private val repo: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}