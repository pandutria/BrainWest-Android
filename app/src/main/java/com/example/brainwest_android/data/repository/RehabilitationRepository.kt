package com.example.brainwest_android.data.repository

import com.example.brainwest_android.data.model.Rehabilitation
import com.example.brainwest_android.data.model.RehabilitationVideo
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.request.VideoByRehabRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class RehabilitationRepository {
    suspend fun getRehabById(id: Int): Response<BaseResponse<List<RehabilitationVideo>>> {
        val res = RetrofitInstance.api.getRehabById(id)
        return res
    }

    suspend fun getVideoByRehab(
        age: String,
        gender: String,
        medical_status: String,
        time_of_diagnosis: String
    ): Response<BaseResponse<List<RehabilitationVideo>>> {
        val res = RetrofitInstance.api.getVideoByRehab(VideoByRehabRequest(age, gender, medical_status, time_of_diagnosis))
        return res
    }

    suspend fun getVideoRehabById(id: Int): Response<BaseResponse<RehabilitationVideo>> {
        val res = RetrofitInstance.api.getVideoRehabById(id)
        return res
    }
}