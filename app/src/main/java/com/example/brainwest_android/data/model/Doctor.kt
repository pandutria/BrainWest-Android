package com.example.brainwest_android.data.model

data class Doctor(
    val id: Int,
    val specialization: String,
    val hospital: String,
    val rating: String,
    val image: String,
    val experience: Int,
    val user: User
)
