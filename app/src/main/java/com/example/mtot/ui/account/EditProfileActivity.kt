package com.example.mtot.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import com.example.mtot.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){

        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }

        binding.buttonNext.setOnClickListener {
            finish()
        }

    }
}