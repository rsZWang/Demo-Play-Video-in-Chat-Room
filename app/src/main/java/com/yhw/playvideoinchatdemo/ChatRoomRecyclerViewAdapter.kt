package com.yhw.playvideoinchatdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yhw.playvideoinchatdemo.databinding.ViewHolderChatRoomTextBinding
import com.yhw.playvideoinchatdemo.databinding.ViewHolderChatRoomVideoBinding

class ChatRoomRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TEXT = 1
        private const val PICTURE = 2
        private const val VIDEO = 3
    }

    private val messageTypeList = ArrayList<MessageType>()

    fun add(message: MessageType) {
        messageTypeList.add(message)
        notifyDataSetChanged()
//        notifyItemInserted(messageTypeList.size+1)
    }

    override fun getItemCount(): Int = messageTypeList.size

    override fun getItemViewType(position: Int): Int =
        when (messageTypeList[position]) {
            is MessageText -> TEXT
            is MessagePicture -> PICTURE
            else  -> VIDEO
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TEXT -> TextViewHolder(ViewHolderChatRoomTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            PICTURE -> TextViewHolder(ViewHolderChatRoomTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> VideoViewHolder(ViewHolderChatRoomVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> holder.bind(messageTypeList[position])
            is VideoViewHolder -> holder.bind(messageTypeList[position])
        }
    }

    private inner class TextViewHolder(val binding: ViewHolderChatRoomTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: MessageType) {
            binding.textGchatMessageMe.text = (text as MessageText).text
        }
    }

    private inner class VideoViewHolder(val binding: ViewHolderChatRoomVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: MessageType) {

        }
    }

}