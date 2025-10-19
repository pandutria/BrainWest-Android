package com.example.brainwest_android.data.repository

import android.content.Context
import com.example.brainwest_android.data.local.GeneralPref
import com.example.brainwest_android.data.local.TokenPref
import com.example.brainwest_android.data.model.CommunityHistoryMessage
import com.example.brainwest_android.data.model.CommunityMessage
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.request.CommunityMessageRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import com.example.brainwest_android.utils.Helper
import com.google.firebase.database.*
import retrofit2.Response

class ChatCommunityRepository(private val context: Context) {
    private val database = FirebaseDatabase
        .getInstance("https://brainwest-ce733-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .reference

    fun sendMessage(groupId: Int, message: String) {
        val userId = GeneralPref(context).getUserId()
        val userName = GeneralPref(context).getFullname()
        val messageRef = database.child("community_chats").child(groupId.toString()).child("messages").push()

        val messageData = CommunityMessage(
            sender_id = userId,
            sender_name = userName,
            message = message,
            timestamp = System.currentTimeMillis()
        )

        messageRef.setValue(messageData)
            .addOnSuccessListener {
                Helper.showErrorLog("Message sent successfully to Firebase")
            }
            .addOnFailureListener { e ->
                Helper.showErrorLog("Failed to send message: ${e.message}")
            }
    }

    fun listenMessages(groupId: Int, callback: (List<CommunityMessage>) -> Unit) {
        val chatRef = database.child("community_chats").child(groupId.toString()).child("messages")

        chatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<CommunityMessage>()
                for (child in snapshot.children) {
                    val msg = child.getValue(CommunityMessage::class.java)
                    if (msg != null) messages.add(msg)
                }
                callback(messages.sortedBy { it.timestamp })
            }

            override fun onCancelled(error: DatabaseError) {
                Helper.showErrorLog(error.message)
            }
        })
    }

    suspend fun sendMessageToApi(message: String, groupId: Int): Response<BaseResponse<CommunityHistoryMessage>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.sendMessageCommunity("Bearer $token", CommunityMessageRequest(groupId, message))
        return res
    }
}
