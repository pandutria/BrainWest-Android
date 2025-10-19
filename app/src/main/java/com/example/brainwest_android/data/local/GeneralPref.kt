package com.example.brainwest_android.data.local

import android.content.Context

class GeneralPref(context: Context) {
    val key = "name_key"
    val pref = "name_pref"
    val keyId = "id_key"
    val rehab_key = "rehab_key"
    val rehab_pref = "rehab_pref"

    val shared = context.getSharedPreferences(pref, Context.MODE_PRIVATE)

    fun saveFullname(name: String) {
        shared.edit().putString(key, name).apply()
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

    fun saveRehabId(id: Int) {
        shared.edit().putInt(rehab_key, id).apply()
    }

    fun getRehabId(): Int {
        return shared.getInt(rehab_key, 0)
    }

}