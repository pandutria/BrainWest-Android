package com.example.brainwest_android.data.model

data class ProductTransactionDetail(
    val id: Int,
    val product: Product,
    val product_transaction_header_id: Int,
    val qty: Int
)
