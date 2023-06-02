package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtot.R
import com.example.mtot.databinding.ActivityPostDetailBinding

class PostDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityPostDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}