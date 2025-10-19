package com.example.brainwest_android.data.model

data class CommunityHistoryMessage (
    val id: Int,
    val message: String,
    val group: Community,
    val sender: User
)