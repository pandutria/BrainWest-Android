package com.example.brainwest_android.ui.product.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.data.repository.ProductRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.product.list.ListProductViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class DetailProductViewModel(val repo: ProductRepository): ViewModel() {
    private val _result = MutableLiveData<State<Product>>()
    val result: MutableLiveData<State<Product>> = _result

    fun getProductById(id: Int) {
        viewModelScope.launch {
            _result.postValue(State.Loading)
            try {
                val res = repo.getProductById(id)
                if (res.isSuccessful) _result.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _result.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch(e: Exception) {
                _result.postValue(State.Error(e.message.toString()))
            }
        }
    }
}

class DetailProductViewModelFactory(private val repo: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}