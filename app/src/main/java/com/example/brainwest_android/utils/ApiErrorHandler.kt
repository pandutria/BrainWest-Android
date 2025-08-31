package com.example.brainwest_android.utils

import org.json.JSONObject
import retrofit2.Response

object ApiErrorHandler {
    fun parseError(response: Response<*>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            if (errorBody.isNullOrEmpty()) {
                "Terjadi kesalahan"
            } else {
                val json = JSONObject(errorBody)
                json.optString("message", "Terjadi kesalahan")
            }
        } catch (e: Exception) {
            "Terjadi kesalahan: ${e.message}"
        }
    }
}
