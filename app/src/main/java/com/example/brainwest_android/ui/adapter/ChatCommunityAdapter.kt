package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.data.model.CommunityMessage
import com.example.brainwest_android.databinding.ItemMessageReceiverCommunityBinding
import com.example.brainwest_android.databinding.ItemMessageSenderCommunityBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatCommunityAdapter(
    private val messages: MutableList<CommunityMessage>,
    private val currentUserId: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    private fun formatTimestamp(timestamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val netDate = Date(timestamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            ""
        }
    }

    inner class SentMessageViewHolder(val binding: ItemMessageSenderCommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: CommunityMessage) {
            binding.tvChat.text = message.message
            binding.tvTime.text = formatTimestamp(message.timestamp)
        }
    }

    inner class ReceivedMessageViewHolder(val binding: ItemMessageReceiverCommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: CommunityMessage) {
            binding.tvUsername.text = message.sender_name
            binding.tvChat.text = message.message
            binding.tvTime.text = formatTimestamp(message.timestamp)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.sender_id == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_SENT) {
            val binding = ItemMessageSenderCommunityBinding.inflate(inflater, parent, false)
            SentMessageViewHolder(binding)
        } else {
            val binding = ItemMessageReceiverCommunityBinding.inflate(inflater, parent, false)
            ReceivedMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is SentMessageViewHolder -> holder.bind(message)
            is ReceivedMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    fun setMessages(newMessages: List<CommunityMessage>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }



//    fun addMessage(message: CommunityMessage) {
//        messages.add(message)
//        notifyItemInserted(messages.size - 1)
//    }
}