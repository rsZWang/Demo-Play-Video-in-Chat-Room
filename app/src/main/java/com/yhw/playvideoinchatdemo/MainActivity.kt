package com.yhw.playvideoinchatdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yhw.playvideoinchatdemo.databinding.ActivityMainBinding
import net.alhazmy13.mediapicker.Image.ImagePicker
import net.alhazmy13.mediapicker.Video.VideoPicker


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewAdapter = ChatRoomRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding.chatRecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = viewAdapter
        }

        binding.albumButton.setOnClickListener {
            VideoPicker.Builder(this)
                .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
                .enableDebuggingMode(true)
                .directory(VideoPicker.Directory.DEFAULT)
                .build()
        }

        binding.sendButton.setOnClickListener {
            Log.i(TAG, "SEND!!")
            val text = binding.textEditText.text.toString()
            if (text.isNotEmpty()) {
                viewAdapter.add(MessageText(text))
                binding.textEditText.text?.clear()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(binding.textEditText.windowToken, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i(TAG, "requestCode: $requestCode,  resultCode: $resultCode")

        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val paths = data?.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH)
            Log.i(TAG, "PATHS: $paths")
            viewAdapter.add(MessageVideo(paths!!.first()))
        }

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val mPaths: List<String>? = data?.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH)
            //Your Code
        }
    }

}