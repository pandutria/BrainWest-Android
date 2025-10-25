package com.example.brainwest_android.data.network.api.ai.detection

import com.example.brainwest_android.data.model.Prediction
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DetectionApiService {
    @Multipart
    @POST("predict")
    suspend fun prediction(@Part image: MultipartBody.Part): Response<Prediction>
}