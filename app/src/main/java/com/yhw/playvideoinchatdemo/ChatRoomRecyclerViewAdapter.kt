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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.yhw.playvideoinchatdemo.databinding.ViewHolderChatRoomYtBinding
import java.io.File

class ChatRoomRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "ChatRoomRecyclerViewAdapter"
        private const val TEXT = 1
        private const val PICTURE = 2
        private const val VIDEO = 3
        private const val YT = 4
    }

    private val messageTypeList = ArrayList<MessageType>()

    fun add(message: MessageType) {
        messageTypeList.add(message)
        notifyItemInserted(messageTypeList.size+1)
    }

    override fun getItemCount(): Int = messageTypeList.size

    override fun getItemViewType(position: Int): Int =
        when (messageTypeList[position]) {
            is MessagePicture -> PICTURE
            is MessageVideo -> VIDEO
            is MessageYT -> YT
            else -> TEXT
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TEXT -> TextViewHolder(ViewHolderChatRoomTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            PICTURE -> TextViewHolder(ViewHolderChatRoomTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            YT -> YTViewHolder(ViewHolderChatRoomYtBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> VideoViewHolder(ViewHolderChatRoomVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> holder.bind(messageTypeList[position])
            is VideoViewHolder -> holder.bind(messageTypeList[position])
            is YTViewHolder -> holder.bind(messageTypeList[position])
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

            val newLayoutParams = binding.videoView.layoutParams
            newLayoutParams.width *= videoWidth.toInt()
            newLayoutParams.height *= videoHeight.toInt()
            Log.i("ADAPTER", "width: ${newLayoutParams.width}")
            Log.i("ADAPTER", "height: ${newLayoutParams.height}")
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

    private inner class YTViewHolder(val binding: ViewHolderChatRoomYtBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ytMessage: MessageType) {
            val ytId = (ytMessage as MessageYT).ytId
            with(binding.youtubePlayerView.getPlayerUiController()) {
                showVideoTitle(false)
                showMenuButton(false)
                showBufferingProgress(false)
                showCurrentTime(false)
                showDuration(false)
                showSeekBar(false)
                showFullscreenButton(false)
                showUi(false)
                showCustomAction1(false)
                showCustomAction1(false)
                showYouTubeButton(false)
            }
            binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)

                    youTubePlayer.loadVideo("Ow3awqJwg1E", 0F)
                    youTubePlayer.addListener(object : YouTubePlayerListener {
                        override fun onApiChange(youTubePlayer: YouTubePlayer) { }
                        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) { }
                        override fun onError(
                            youTubePlayer: YouTubePlayer,
                            error: PlayerConstants.PlayerError
                        ) { }

                        override fun onPlaybackQualityChange(
                            youTubePlayer: YouTubePlayer,
                            playbackQuality: PlayerConstants.PlaybackQuality
                        ) { }

                        override fun onPlaybackRateChange(
                            youTubePlayer: YouTubePlayer,
                            playbackRate: PlayerConstants.PlaybackRate
                        ) { }

                        override fun onReady(youTubePlayer: YouTubePlayer) {  }

                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerConstants.PlayerState
                        ) {
                            if (state == PlayerConstants.PlayerState.ENDED) {
                                youTubePlayer.play()
                            }
                        }

                        override fun onVideoDuration(
                            youTubePlayer: YouTubePlayer,
                            duration: Float
                        ) { }

                        override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) { }

                        override fun onVideoLoadedFraction(
                            youTubePlayer: YouTubePlayer,
                            loadedFraction: Float
                        ) {  }
                    })
                }
            })
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