package com.example.brainwest_android.data.model

data class Donation(
    val id: Int? = null,
    val title: String? = null,
    val image: String? = null,
    val desc: String? = null,
    val institution: String? = null,
    val image_institution: String? = null,
    val date: String? = null,
    val target: Int? = null,
    val deadline: String? = null,
    val current_donate: Int? = null,
    val user_donate: List<User>? = null
)