package com.example.brainwest_android.data.network.api

import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.data.model.EventTransaction
import com.example.brainwest_android.data.model.MidtransEventTransaction
import com.example.brainwest_android.data.model.User
import com.example.brainwest_android.data.network.request.EventTransactionRequest
import com.example.brainwest_android.data.network.request.LoginRequest
import com.example.brainwest_android.data.network.request.RegisterRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import com.example.brainwest_android.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<BaseResponse<User>>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("education")
    suspend fun getAllEducation(@Header("Authorization") token: String): Response<BaseResponse<List<Education>>>

    @GET("education/{id}")
    suspend fun getEducationById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<BaseResponse<Education>>

    @GET("events")
    suspend fun getAllEvent(@Header("Authorization") token: String): Response<BaseResponse<List<Event>>>

    @GET("events/{id}")
    suspend fun getEventById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<BaseResponse<Event>>

    @POST("event/transaction")
    suspend fun postEventTransaction(
        @Header("Authorization") token: String,
        @Body request: EventTransactionRequest
    ): Response<BaseResponse<MidtransEventTransaction>>

    @GET("me/event/transaction")
    suspend fun getMyEventTransaction(@Header("Authorization") token: String): Response<BaseResponse<List<EventTransaction>>>
}