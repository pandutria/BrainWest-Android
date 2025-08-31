package com.example.brainwest_android.data.repository

import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class EducationRepository {
    suspend fun getAllArticle(): Response<BaseResponse<List<Education>>> {
        val res = RetrofitInstance.api.getAllArticle()
        return res
    }

    suspend fun getAllVideos(): Response<BaseResponse<List<Education>>> {
        val res = RetrofitInstance.api.getAllVideo()
        return res
    }
}