package com.example.brainwest_android.data.network.api.ai

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DetectionRetrofitInstance {
    val baseurl = "https://Rayzen7-Brain-Detection.hf.space/"
    private val client by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    val api: DetectionApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DetectionApiService::class.java)
    }
}