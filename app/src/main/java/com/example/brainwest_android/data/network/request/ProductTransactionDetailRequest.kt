package com.example.brainwest_android.data.network.request

data class ProductTransactionDetailRequest(
    val product_id: Int,
    val product_transaction_header_id: Int,
    val qty: Int
)
