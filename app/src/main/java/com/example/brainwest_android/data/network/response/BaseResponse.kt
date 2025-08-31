package com.example.brainwest_android.data.network.response

data class BaseResponse<T>(
    val message: String,
    val data: T
)