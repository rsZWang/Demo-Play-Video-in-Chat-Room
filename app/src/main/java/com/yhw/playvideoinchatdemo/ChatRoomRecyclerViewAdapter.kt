package com.yhw.playvideoinchatdemo

import android.content.res.Resources
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yhw.playvideoinchatdemo.databinding.ViewHolderChatRoomTextBinding
import com.yhw.playvideoinchatdemo.databinding.ViewHolderChatRoomVideoBinding
import android.util.DisplayMetrics
import android.R.string.no
import android.util.TypedValue
import java.io.File

class ChatRoomRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "ChatRoomRecyclerViewAdapter"
        private const val TEXT = 1
        private const val PICTURE = 2
        private const val VIDEO = 3
    }

    private val messageTypeList = ArrayList<MessageType>()

    fun add(message: MessageType) {
        messageTypeList.add(message)
//        notifyDataSetChanged()
        notifyItemInserted(messageTypeList.size+1)
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
            val uriString = (text as MessageVideo).videoUri
            val uri = Uri.fromFile(File(uriString))
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(itemView.context, uri)
            var videoWidth = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)!!.toFloat()
            var videoHeight = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)!!.toFloat()
            retriever.release()

            val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
            val maxWidth = ((displayMetrics.widthPixels.toFloat()/3)*2)
            if (videoWidth > maxWidth) {
                val scale = maxWidth/videoWidth
                videoWidth = maxWidth
                videoHeight *= scale
            }

            val factor = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, Resources.getSystem().displayMetrics)
            val newLayoutParams = binding.videoView.layoutParams
            newLayoutParams.width = (videoWidth * factor).toInt()
            newLayoutParams.height = (videoHeight * factor).toInt()
            binding.videoView.requestLayout()
            binding.videoView.setOnPreparedListener {
                binding.videoView.start()
            }
            var counter = 0
            val max = 2
            binding.videoView.setOnCompletionListener {
                if (counter < max) {
                    counter += 1
                    binding.videoView.seekTo(0)
                    binding.videoView.start()
                }
            }
            binding.videoView.setVideoURI(uri)
        }
    }

    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Float.dp: Float
        get() = (this / Resources.getSystem().displayMetrics.density)
    val Float.px: Float
        get() = (this * Resources.getSystem().displayMetrics.density)

}