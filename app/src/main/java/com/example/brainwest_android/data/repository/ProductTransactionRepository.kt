package com.example.brainwest_android.data.repository

import android.content.Context
import com.example.brainwest_android.data.local.TokenPref
import com.example.brainwest_android.data.model.MidtransProductTransactionHeader
import com.example.brainwest_android.data.model.ProductTransactionDetail
import com.example.brainwest_android.data.model.ProductTransactionHeader
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.request.ProductTransactionDetailRequest
import com.example.brainwest_android.data.network.request.ProductTransactionHeaderRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class ProductTransactionRepository {
    suspend fun postTransactionHeader(
        total: Int,
        address: String,
        context: Context
    ): Response<BaseResponse<MidtransProductTransactionHeader>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.postProductHeader("Bearer $token", ProductTransactionHeaderRequest(total, address))
        return res
    }
    suspend fun postTransactionDetail(
        product_id: Int,
        product_transaction_header_id: Int,
        qty: Int): Response<BaseResponse<ProductTransactionHeader>> {
        val res = RetrofitInstance.api.postProductDetail(ProductTransactionDetailRequest(product_id, product_transaction_header_id, qty))
        return res
    }
    suspend fun getTransactionHeaderByUser(context: Context): Response<BaseResponse<List<ProductTransactionHeader>>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.getProductHeaderByUser("Bearer $token")
        return res
    }
    suspend fun getTransactionDetail(): Response<BaseResponse<List<ProductTransactionDetail>>> {
        val res = RetrofitInstance.api.getProducrDetail()
        return res
    }
    suspend fun deleteTransactionHeader(id: Int): Response<BaseResponse<ProductTransactionHeader>> {
        val res = RetrofitInstance.api.deleteProductHeader(id)
        return res
    }
}