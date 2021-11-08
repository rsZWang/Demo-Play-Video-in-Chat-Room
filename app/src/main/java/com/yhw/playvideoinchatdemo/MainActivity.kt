package com.yhw.playvideoinchatdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yhw.playvideoinchatdemo.databinding.ActivityMainBinding
import net.alhazmy13.mediapicker.Image.ImagePicker
import net.alhazmy13.mediapicker.Video.VideoPicker


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
//    private val launcher = registerImagePicker { images ->
//        // Selected images are ready to use
//        if(images.isNotEmpty()){
//            val sampleImage = images[0]
//            Glide.with(this@MainActivity)
//                .load(sampleImage.uri)
//                .into(imageView)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.sendButton.setOnClickListener {
            val text = binding.textEditText.toString()
            if (text.isNotEmpty()) {
                binding.textEditText.text?.clear()
            }
        }

//        ImagePicker.Builder(this@MainActivity)
//            .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
//            .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
//            .directory(ImagePicker.Directory.DEFAULT)
//            .extension(ImagePicker.Extension.PNG)
//            .scale(600, 600)
//            .allowMultipleImages(false)
//            .enableDebuggingMode(true)
//            .build()

        VideoPicker.Builder(this)
            .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
            .enableDebuggingMode(true)
            .directory(VideoPicker.Directory.DEFAULT)
            .build()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i(TAG, "requestCode: $requestCode,  resultCode: $resultCode")

        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val paths = data?.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH)
            Log.i(TAG, "PATHS: $paths")

        }

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val mPaths: List<String>? = data?.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH)
            //Your Code
        }
    }

}