package com.example.brainwest_android.data.local

import android.content.Context

class TokenPref(context: Context) {
    val tokenPref = "token_pref"
    val tokenKey = "token_key"
  
    val shared = context.getSharedPreferences(tokenPref, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        shared.edit().putString(tokenKey, token).apply()
    }

    fun getToken(): String {
        return shared.getString(tokenKey, "").toString()
    }

    fun removeToken() {
        shared.edit().remove(tokenKey).apply()
    }
}