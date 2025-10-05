package com.example.brainwest_android.data.model

data class ConsultationHistoryMessage(
    val user: User,
    val doctor: Doctor,
    val last_message: String,
    val last_message_at: String
)
