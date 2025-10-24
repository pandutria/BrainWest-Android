package com.example.brainwest_android.data.model

import androidx.resourceinspection.annotation.Attribute.IntMap

data class Cart (
    var qty: Int,
    val product: Product
)