package com.example.brainwest_android.data.repository

import android.content.Context
import android.util.Log
import com.example.brainwest_android.data.local.TokenPref
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class EducationRepository {
    suspend fun getAllEducation(context: Context): Response<BaseResponse<List<Education>>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.getAllEducation("Bearer $token")
        return res
    }

    suspend fun getEducationById(context: Context, id: Int): Response<BaseResponse<Education>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.getEducationById("Bearer $token", id)
        return res
    }
}