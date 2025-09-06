package com.example.brainwest_android.data.repository

import com.example.brainwest_android.data.network.api.gemini.GeminiApiService
import com.example.brainwest_android.data.network.request.gemini.Content
import com.example.brainwest_android.data.network.request.gemini.GeminiRequest
import com.example.brainwest_android.data.network.request.gemini.Part

class GeminiRepository(private val api: GeminiApiService) {
    suspend fun sendMessage(apiKey: String, message: String): String? {
        val request = GeminiRequest(contents = listOf(
                Content(parts = listOf(Part(text = message)))
            )
        )

        val response = api.generateContent(apiKey, request)
        return if (response.isSuccessful) {
            response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
        } else {
            null
        }
    }
}