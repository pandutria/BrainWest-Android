package com.example.brainwest_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brainwest_android.R
import com.example.brainwest_android.data.model.CommunityMessage

class ChatCommunityAdapter(
    private val messages: MutableList<CommunityMessage>,
    private val currentUserId: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMessage: TextView = itemView.findViewById(R.id.tvChat)
//        private val tvName: TextView = itemView.findViewById(R.id.tvSenderName)
        fun bind(message: CommunityMessage) {
            tvMessage.text = message.message
//            tvName.text = "You"
        }
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMessage: TextView = itemView.findViewById(R.id.tvChat)
//        private val tvName: TextView = itemView.findViewById(R.id.tvSenderName)
        fun bind(message: CommunityMessage) {
            tvMessage.text = message.message
//            tvName.text = message.sender_name
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.sender_id == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_sender, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_receiver, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is SentMessageViewHolder) holder.bind(message)
        else if (holder is ReceivedMessageViewHolder) holder.bind(message)
    }

    fun setMessages(newMessages: List<CommunityMessage>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    fun addMessage(message: CommunityMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
}
