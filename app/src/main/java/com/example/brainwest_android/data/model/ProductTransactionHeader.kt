package com.example.brainwest_android.data.model

data class ProductTransactionHeader (
    val id: Int,
    val total: Int,
    val address: String,
    val user: User,
    val created_at: String
)