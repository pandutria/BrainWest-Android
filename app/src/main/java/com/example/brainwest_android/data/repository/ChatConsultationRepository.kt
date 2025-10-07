package com.example.brainwest_android.data.repository

import android.content.Context
import android.util.Log
import com.example.brainwest_android.data.local.TokenPref
import com.example.brainwest_android.data.model.ConsultationHistoryMessage
import com.example.brainwest_android.data.model.ConsultationMessage
import com.example.brainwest_android.data.network.api.RetrofitInstance
import com.example.brainwest_android.data.network.request.ConsultationRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import com.example.brainwest_android.utils.Helper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Response

class ChatConsultationRepository(val context: Context) {
    private val database = FirebaseDatabase
        .getInstance("https://brainwest-ce733-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .reference

    fun generateChatId(userId: Int, doctorId: Int): String {
        return if (userId < doctorId) "${userId}_${doctorId}" else "${doctorId}_${userId}"
    }

    fun sendMessage(userId: Int, doctorId: Int, message: String) {
        val chatId = generateChatId(userId, doctorId)
        val messageRef = database.child("chats").child(chatId).push()

        val messageData = ConsultationMessage(
            sender_id = userId,
            receiver_id = doctorId,
            message = message,
            timestamp = System.currentTimeMillis()
        )

        messageRef.setValue(messageData)

        messageRef.setValue(messageData)
            .addOnSuccessListener {
                Helper.showErrorLog("Message sent successfully to Firebase")
            }
            .addOnFailureListener { e ->
                Helper.showErrorLog("Failed to send message: ${e.message}")
            }
    }

    suspend fun sendMessageToAPI(doctorId: Int, message: String): Response<BaseResponse<List<ConsultationHistoryMessage>>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.sendMessageConsultation("Bearer $token", ConsultationRequest(doctorId, message))
        Log.d("tokenDebug", "token : $token")
        Log.d("apiDebug", "raw response: ${res.raw()}") // Tambahkan ini
        Log.d("bodyDebug", "response body: ${res.errorBody()?.string()}") // Lihat isi error-nya
        return res
    }

    fun listenMessages(userId: Int, doctorId: Int, callback: (List<ConsultationMessage>) -> Unit) {
        val chatId = if (doctorId < userId) "${doctorId}_${userId}" else "${userId}_${doctorId}"
        val chatRef = FirebaseDatabase.getInstance("https://brainwest-ce733-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("chats").child(chatId)

        chatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<ConsultationMessage>()
                for (child in snapshot.children) {
                    val msg = child.getValue(ConsultationMessage::class.java)
                    if (msg != null) messages.add(msg)
                }
                callback(messages)
            }

            override fun onCancelled(e: DatabaseError) {
                Helper.showErrorLog(e.message)
            }
        })
    }

    suspend fun getHistory(): Response<BaseResponse<List<ConsultationHistoryMessage>>> {
        val token = TokenPref(context).getToken()
        val res = RetrofitInstance.api.getHistoryConsultation("Bearer $token")
        return res
    }
}