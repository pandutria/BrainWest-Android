package com.example.brainwest_android.data.repository

import com.example.brainwest_android.data.model.Doctor
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class ConsultationRepository {
    suspend fun getDoctor(): Response<BaseResponse<List<Doctor>>> {
        val res = RetrofitInstance.api.getDoctor()
        return res
    }
    suspend fun getDoctorById(id: Int): Response<BaseResponse<Doctor>> {
        val res = RetrofitInstance.api.getDoctorById(id)
        return res
    }
}