package com.example.brainwest_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RehabilitationVideo(
    val id: Int,
    val title: String,
    val rehabilitation_id: Int,
    val thumbnail: String,
    val link: String,
    val text: String
): Parcelable

