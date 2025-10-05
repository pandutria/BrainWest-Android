package com.example.brainwest_android.data.model

data class ConsultationMessage(
    val sender_id: Int = 0,
    val receiver_id: Int = 0,
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
