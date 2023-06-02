package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.mtot.R
import com.example.mtot.databinding.ActivityAddJourneyBinding
import com.example.mtot.retrofit2.GroupInterface
import com.example.mtot.retrofit2.GroupObject.groupInterface

class AddJourney : AppCompatActivity() {
    lateinit var binding : ActivityAddJourneyBinding
    lateinit var dataList : ArrayList<String>
    lateinit var adapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJourneyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){

        val groupService = groupInterface.getTeams()

        dataList = arrayListOf(
            "그룹1",
            "그룹2"
        )

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dataList)
        binding.spGroupSelect.adapter = adapter

    }

}