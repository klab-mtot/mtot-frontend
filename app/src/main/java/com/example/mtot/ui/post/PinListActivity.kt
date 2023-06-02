package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.R
import com.example.mtot.databinding.ActivityPinListBinding

class PinListActivity : AppCompatActivity() {
    lateinit var binding: ActivityPinListBinding
    lateinit var dataList : List<PinData>
    private lateinit var adapter: PinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPinListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.pinRecyclerView.layoutManager = GridLayoutManager(this, 3)
        dataList = arrayListOf(
            PinData(R.drawable.ic_bottom_navigation_plane),
            PinData(R.drawable.ic_bottom_navigation_plane),
            PinData(R.drawable.ic_bottom_navigation_plane),
            PinData(R.drawable.ic_bottom_navigation_plane),
        )
        adapter = PinAdapter(dataList)
        binding.pinRecyclerView.adapter = adapter
    }
}