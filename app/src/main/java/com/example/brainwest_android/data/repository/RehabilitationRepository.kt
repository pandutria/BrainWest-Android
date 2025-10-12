package com.example.brainwest_android.data.repository

import com.example.brainwest_android.data.model.RehabilitationVideo
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.request.VideoByRehabRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class RehabilitationRepository {
    suspend fun getVideoByRehab(
        age: String,
        gender: String,
        medical_status: String,
        time_of_diagnosis: String
    ): Response<BaseResponse<List<RehabilitationVideo>>> {
        val res = RetrofitInstance.api.getVideoByRehab(VideoByRehabRequest(age, gender, medical_status, time_of_diagnosis))
        return res
    }
}