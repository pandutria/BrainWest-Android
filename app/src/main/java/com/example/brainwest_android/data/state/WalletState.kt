package com.example.brainwest_android.data.state

sealed class WalletState {
    data class Success(val balance: Int) : WalletState()
    data class Error(val message: String) : WalletState()
}