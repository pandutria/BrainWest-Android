package com.example.brainwest_android.data.network.api.ai.diagnose

import com.example.brainwest_android.data.model.Diagnose
import com.example.brainwest_android.data.model.Prediction
import com.example.brainwest_android.data.network.request.DiagnoseRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DiagnoseApiService {
    @POST("predict")
    suspend fun diagnose(@Body request: DiagnoseRequest): Response<Diagnose>
}