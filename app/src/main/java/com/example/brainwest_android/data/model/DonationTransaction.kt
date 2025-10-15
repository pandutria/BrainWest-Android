package com.example.brainwest_android.data.model

data class DonationTransaction(
    val id: Int? = null,
    val user: User? = null,
    val donate: Donation? = null,
    val total_donate: Long? = null
)
