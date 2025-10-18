package com.example.brainwest_android.data.repository

import com.example.brainwest_android.data.model.Community
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class CommunityRepository {
    suspend fun getCommunity(): Response<BaseResponse<List<Community>>> {
        val res = RetrofitInstance.api.getAllCommunity()
        return res
    }
}