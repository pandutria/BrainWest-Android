package com.example.brainwest_android.data.network.response

import com.example.brainwest_android.data.model.User

data class LoginResponse(
    val message: String? = null,
    val token: String? = null,
    val data: User
)
