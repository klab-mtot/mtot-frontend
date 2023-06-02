package com.example.mtot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtot.R
import com.example.mtot.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        binding.emailButton.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            i.putExtra("mode", "login")
            startActivity(i)
        }
        binding.googleButton.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            i.putExtra("mode", "profile")
            startActivity(i)
        }
    }
}