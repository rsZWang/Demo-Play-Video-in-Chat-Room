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

    override fun getItemCount(): Int = messageTypeList.size

    override fun getItemViewType(position: Int): Int =
        when (messageTypeList[position]) {
            is MessageText -> TEXT
            is MessagePicture -> PICTURE
            else  -> VIDEO
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TEXT -> TextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_room_text, parent, false))
            PICTURE -> TextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_room_text, parent, false))
            else -> VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_room_video, parent, false))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> holder.bind()
            is VideoViewHolder -> holder.bind()
        }
    }

    private inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ViewHolderChatRoomTextBinding.bind(view)

        fun bind() {



        }
    }

    private inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ViewHolderChatRoomVideoBinding.bind(view)

        fun bind() {

        }
    }

}