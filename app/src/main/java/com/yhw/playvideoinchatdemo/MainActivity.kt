package com.yhw.playvideoinchatdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lassi.common.utils.KeyUtils
import com.lassi.data.media.MiMedia
import com.lassi.domain.media.LassiOption
import com.lassi.domain.media.MediaType
import com.lassi.presentation.builder.Lassi
import com.yhw.playvideoinchatdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewAdapter = ChatRoomRecyclerViewAdapter()

    private lateinit var receiveData: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding.chatRecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = viewAdapter
        }

        binding.albumButton.setOnClickListener {
            setupPicker()
        }

        binding.sendButton.setOnClickListener {
            val text = binding.textEditText.text.toString()
            if (text.isNotEmpty()) {
                viewAdapter.add(MessageText(text))
                binding.textEditText.text?.clear()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(binding.textEditText.windowToken, 0)
            }
        }

        receiveData = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedMedia = it.data?.getSerializableExtra(KeyUtils.SELECTED_MEDIA) as ArrayList<MiMedia>
                if (!selectedMedia.isNullOrEmpty()) {
                    viewAdapter.add(MessageVideo(selectedMedia[0].path!!))
                }
            }
        }
    }

    private fun setupPicker() {

        val intent = Lassi(this)
            .with(LassiOption.CAMERA_AND_GALLERY) // choose Option CAMERA, GALLERY or CAMERA_AND_GALLERY
            .setMaxCount(5)
            .setGridSize(3)
            .setMediaType(MediaType.VIDEO) // MediaType : VIDEO IMAGE, AUDIO OR DOC
//            .setCompressionRation(10) // compress image for single item selection (can be 0 to 100)
//            .setMinTime(15) // for MediaType.VIDEO only
//            .setMaxTime(30) // for MediaType.VIDEO only
//            .setSupportedFileTypes("mp4", "mkv", "webm", "avi", "flv", "3gp") // Filter by limited media format (Optional)
//            .setMinFileSize(100) // Restrict by minimum file size
//            .setMaxFileSize(1024) //  Restrict by maximum file size
            .disableCrop() // to remove crop from the single image selection (crop is enabled by default for single image)
            /*
             * Configuration for  UI
             */
//            .setStatusBarColor(R.color.colorPrimaryDark)
//            .setToolbarResourceColor(R.color.colorPrimary)
//            .setProgressBarColor(R.color.colorAccent)
//            .setPlaceHolder(R.drawable.ic_image_placeholder)
//            .setErrorDrawable(R.drawable.ic_image_placeholder)
//            .setSelectionDrawable(R.drawable.ic_checked_media)
//            .setCropType(CropImageView.CropShape.RECTANGLE) // choose shape for cropping after capturing an image from camera (for MediaType.IMAGE only)
//            .setCropAspectRatio(1, 1) // define crop aspect ratio for cropping after capturing an image from camera (for MediaType.IMAGE only)
//            .enableFlip() // Enable flip image option while image cropping (for MediaType.IMAGE only)
//            .enableRotate() // Enable rotate image option while image cropping (for MediaType.IMAGE only)
            .enableActualCircleCrop() // Enable actual circular crop (only for MediaType.Image and CropImageView.CropShape.OVAL)
            .build()

        receiveData.launch(intent)
    }

}