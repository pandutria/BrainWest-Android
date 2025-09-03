package com.example.brainwest_android.data.repository

import android.content.Context
import com.example.brainwest_android.data.local.TokenPref
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class EventRepository {
    suspend fun getAllEvent(context: Context): Response<BaseResponse<List<Event>>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.getAllEvent("Bearer $token")
        return res
    }

    suspend fun getEventById(context: Context, id: Int): Response<BaseResponse<Event>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.getEventById("Bearer $token", id)
        return res
    }
}