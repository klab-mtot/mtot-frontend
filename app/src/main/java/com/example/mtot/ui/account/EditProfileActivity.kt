package com.example.mtot.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import com.example.mtot.MainActivity
import com.example.mtot.StartActivity
import com.example.mtot.databinding.ActivityEditProfileBinding
import com.example.mtot.databinding.ActivitySetProfileBinding

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