package com.example.mtot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtot.databinding.ActivityStartBinding
import com.example.mtot.ui.account.SetProfileActivity

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        binding.googleButton.setOnClickListener {
            val i = Intent(this, SetProfileActivity::class.java)
            startActivity(i)
        }
    }
}