package com.example.mtot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import com.example.mtot.MainActivity
import com.example.mtot.StartActivity
import com.example.mtot.databinding.ActivitySetProfileBinding

class SetProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivitySetProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){

        val callback = onBackPressedDispatcher.addCallback {
            val i = Intent(this@SetProfileActivity, StartActivity::class.java)
            startActivity(i)
        }

        binding.textEnd.setOnClickListener {
            val i = Intent(this@SetProfileActivity, MainActivity::class.java)
            startActivity(i)
        }

        binding.buttonNext.setOnClickListener {
            val i = Intent(this@SetProfileActivity, StartActivity::class.java)
            startActivity(i)
        }

    }
}