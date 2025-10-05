package com.example.brainwest_android.data.repository

import android.content.Context
import com.example.brainwest_android.data.local.TokenPref
import com.example.brainwest_android.data.model.User
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.request.LoginRequest
import com.example.brainwest_android.data.network.request.RegisterRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import com.example.brainwest_android.data.network.response.LoginResponse
import retrofit2.Response

class AuthRepository(val context: Context) {
    suspend fun regsiter(
        username: String,
        fullname: String,
        password: String
    ): Response<BaseResponse<User>> {
        val res = RetrofitInstance.api.register(RegisterRequest(username, fullname, password))
        return res
    }

    suspend fun login(
        username: String,
        password: String,
        context: Context
    ): Response<LoginResponse> {
        val res = RetrofitInstance.api.login(LoginRequest(username, password))
        if (res.isSuccessful) TokenPref(context).saveToken(res.body()!!.token!!)
        return res
    }

    suspend fun me(): Response<BaseResponse<User>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.me("Bearer $token")
        return res
    }
}