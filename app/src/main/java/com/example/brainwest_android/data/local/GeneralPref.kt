package com.example.brainwest_android.data.local

import android.content.Context

class GeneralPref(context: Context) {
    val key = "name_key"
    val pref = "name_pref"
    val keyId = "id_key"

    val shared = context.getSharedPreferences(pref, Context.MODE_PRIVATE)

    fun saveFullname(token: String) {
        shared.edit().putString(key, token).apply()
    }

    fun getFullname(): String {
        return shared.getString(key, null).toString()
    }

    fun saveUserId(id: Int) {
        shared.edit().putInt(keyId, id).apply()
    }

    fun getUserId(): Int {
        return shared.getInt(keyId, 0)
    }
}