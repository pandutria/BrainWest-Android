package com.example.brainwest_android.data.repository

import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class ProductRepository {
    suspend fun getProduct(): Response<BaseResponse<List<Product>>> {
        val res = RetrofitInstance.api.getProduct()
        return res
    }
    suspend fun getProductById(id: Int): Response<BaseResponse<Product>> {
        val res = RetrofitInstance.api.getProductById(id)
        return res
    }
}