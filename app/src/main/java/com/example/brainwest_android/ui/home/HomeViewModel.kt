package com.example.brainwest_android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.data.repository.EducationRepository
import com.example.brainwest_android.data.repository.ProductRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.ui.education.EducationViewModel
import com.example.brainwest_android.utils.ApiErrorHandler
import kotlinx.coroutines.launch

class HomeViewModel(val repoProduct: ProductRepository):ViewModel() {
    private val _productResult = MutableLiveData<State<List<Product>>>()
    val productResult: LiveData<State<List<Product>>> get()  = _productResult

    fun getProduct() {
        viewModelScope.launch {
            _productResult.postValue(State.Loading)
            try {
                val res = repoProduct.getProduct()
                if (res.isSuccessful) _productResult.postValue(State.Success(res.body()!!.data, res.body()!!.message!!))
                else _productResult.postValue(State.Error(ApiErrorHandler.parseError(res)))
            } catch (e: Exception) {
                _productResult.postValue(State.Error(e.message.toString()))
            }
        }
    }

}

class HomeViewModelfactory(private val repoProduct: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repoProduct) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}