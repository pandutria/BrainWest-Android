package com.example.brainwest_android.data.repository

import android.content.Context
import com.example.brainwest_android.data.local.TokenPref
import com.example.brainwest_android.data.model.Community
import com.example.brainwest_android.data.model.CommunityHistory
import com.example.brainwest_android.data.model.CommunityHistoryMessage
import com.example.brainwest_android.data.model.CommunityMember
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.request.CommunityMemberRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import retrofit2.Response

class CommunityRepository {
    suspend fun getCommunity(): Response<BaseResponse<List<Community>>> {
        val res = RetrofitInstance.api.getAllCommunity()
        return res
    }
    suspend fun getCommunityById(id: Int): Response<BaseResponse<Community>> {
        val res = RetrofitInstance.api.getCommunity(id)
        return res
    }
    suspend fun postMemberCommunity(context: Context, group_id: Int): Response<BaseResponse<CommunityMember>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.postMemberCommunity("Bearer $token", CommunityMemberRequest(group_id))
        return res
    }
//    suspend fun getHistoryCommunity(context: Context): Response<BaseResponse<List<CommunityHistoryMessage>>> {
//        val token = TokenPref(context).getToken()
//        val res = RetrofitInstance.api.historyCommunityMessage("Bearer $token")
//        return res
//    }
    suspend fun getHistoryCommunity(context: Context): Response<BaseResponse<List<CommunityHistory>>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.getHistoryCommunity("Bearer $token")
        return res
    }
}