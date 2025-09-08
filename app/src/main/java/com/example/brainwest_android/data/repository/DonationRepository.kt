package com.example.brainwest_android.data.repository

import android.content.Context
import com.example.brainwest_android.data.local.TokenPref
import com.example.brainwest_android.data.model.Donation
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class DonationRepository {
    suspend fun getAllDonation(context: Context): Response<BaseResponse<List<Donation>>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.getAllDonation("Bearer $token")
        return res
    }

    suspend fun getDonationById(context: Context, id: Int): Response<BaseResponse<Donation>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.getDonationById("Bearer $token",id)
        return res
    }
}