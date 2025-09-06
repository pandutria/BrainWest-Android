package com.example.brainwest_android.data.network.api.gemini

import com.example.brainwest_android.data.network.request.gemini.GeminiRequest
import com.example.brainwest_android.data.network.response.gemini.GeminiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
}