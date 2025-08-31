package com.example.brainwest_android.data.network.api

import com.example.brainwest_android.data.model.User
import com.example.brainwest_android.data.network.request.LoginRequest
import com.example.brainwest_android.data.network.request.RegisterRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import com.example.brainwest_android.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<BaseResponse<User>>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}