package com.example.mtot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtot.databinding.ActivityJourneyDetailBinding

class JourneyDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityJourneyDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJourneyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}