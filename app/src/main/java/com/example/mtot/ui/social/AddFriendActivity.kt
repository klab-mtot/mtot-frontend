package com.example.mtot.ui.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import com.example.mtot.R
import com.example.mtot.databinding.ActivityAddFriendBinding

class AddFriendActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddFriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        binding.imageView.setOnClickListener {
            finish()
        }
        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }
    }

}