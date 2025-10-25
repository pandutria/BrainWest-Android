package com.example.brainwest_android.data.repository

import com.example.brainwest_android.data.model.Diagnose
import com.example.brainwest_android.data.network.api.ai.diagnose.DiagnoseRetrofitInstance
import com.example.brainwest_android.data.network.request.DiagnoseRequest
import retrofit2.Response

class DiagnoseRepository {
    suspend fun diagnose(question: String, answer: String): Response<Diagnose> {
        val res = DiagnoseRetrofitInstance.api.diagnose(DiagnoseRequest(question, answer))
        return res
    }
}