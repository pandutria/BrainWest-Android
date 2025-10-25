package com.example.brainwest_android.data.repository

import com.example.brainwest_android.data.model.Prediction
import com.example.brainwest_android.data.network.api.ai.detection.DetectionRetrofitInstance
import okhttp3.MultipartBody
import retrofit2.Response

class DetectionRepository {
    suspend fun prediction(image: MultipartBody.Part): Response<Prediction> {
        val res = DetectionRetrofitInstance.api.prediction(image)
        return res
    }
}