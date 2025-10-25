package com.example.brainwest_android.data.network.api.ai.diagnose

import com.example.brainwest_android.data.network.api.ai.detection.DetectionApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DiagnoseRetrofitInstance {
    val baseurl = "https://pandutria-diagnosis.hf.space/"
    private val client by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    val api: DiagnoseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DiagnoseApiService::class.java)
    }
}