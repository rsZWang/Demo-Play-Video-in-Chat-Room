package com.yhw.playvideoinchatdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.yhw.playvideoinchatdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewAdapter = ChatRoomRecyclerViewAdapter()

        with(binding.chatRecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = viewAdapter
        }

        binding.sendButton.setOnClickListener {
            Log.i(TAG, "SEND!!")
            val text = binding.textEditText.text.toString()
            if (text.isNotEmpty()) {
                viewAdapter.add(MessageText(text))
                binding.textEditText.text?.clear()
            }
        }


    }

}