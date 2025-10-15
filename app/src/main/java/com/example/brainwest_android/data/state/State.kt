package com.example.brainwest_android.data.state

sealed class State<out T> {
    data class Success<out T>(val data: T, val message: String) : State<T>()
    data class Error(val message: String) : State<Nothing>()
    data object Loading : State<Nothing>()
}