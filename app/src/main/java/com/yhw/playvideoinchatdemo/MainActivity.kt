package com.yhw.playvideoinchatdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yhw.playvideoinchatdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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


    }

}