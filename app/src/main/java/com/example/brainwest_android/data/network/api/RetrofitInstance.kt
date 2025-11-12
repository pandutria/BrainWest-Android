package com.example.brainwest_android.data.network.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  RetrofitInstance {
    var baseurl = "http://10.0.2.2:8000/api/"
//    val baseurl = "https://brainwest-backend.setionugraha.my.id/api/"
    private val client by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .followRedirects(false)
            .build()
    }

    private val gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}