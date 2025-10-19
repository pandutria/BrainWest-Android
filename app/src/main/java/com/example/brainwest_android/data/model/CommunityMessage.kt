package com.example.brainwest_android.data.model

data class CommunityMessage(
    val sender_id: Int = 0,
    val sender_name: String = "",
    val message: String = "",
    val timestamp: Long = 0
)
