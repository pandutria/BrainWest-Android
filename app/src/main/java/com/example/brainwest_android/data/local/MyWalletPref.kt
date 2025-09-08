package com.example.brainwest_android.data.local

import android.content.Context

class MyWalletPref(context: Context) {
    val walletPref = "wallet_pref"
    val walletKey = "wallet_key"

    val shared = context.getSharedPreferences(walletPref, Context.MODE_PRIVATE)

    fun saveWallet(wallet: Int) {
        shared.edit().putInt(walletKey, wallet).apply()
    }

    fun getWallet(): Int {
        return shared.getInt(walletKey, 0)
    }

    fun plusWallet(plus: Int) {
        val wallet = getWallet()
        val count = wallet + plus
        saveWallet(count)
    }

    fun minusWallet(minus: Int) {
        val wallet = getWallet()
        val count = wallet - minus
        saveWallet(count)
    }
}