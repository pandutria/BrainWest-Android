package com.example.brainwest_android.data.network.api.gemini

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeminiRetrofitIntance {
    val retrofitGemini = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val geminiApi = retrofitGemini.create(GeminiApiService::class.java)
}