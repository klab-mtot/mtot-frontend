package com.example.mtot.ui.social

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import com.example.mtot.R
import com.example.mtot.StartActivity
import com.example.mtot.databinding.ActivityAddGroupBinding

class AddGroupActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddGroupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        binding.imageView2.setOnClickListener {
            finish()
        }
        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }
    }
}