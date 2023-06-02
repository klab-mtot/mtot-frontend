package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.R
import com.example.mtot.databinding.ActivityPinListBinding

class PinListActivity : AppCompatActivity() {
    lateinit var binding: ActivityPinListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PinAdapterGrid
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPinListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.pinRecyclerView
        layoutManager = GridLayoutManager(this, 3)
        // 그리드 형식으로 두 개의 열을 표시하려면 spanCount를 2로 설정합니다.
        recyclerView.layoutManager = layoutManager

        val list = ArrayList<Int>(R.drawable.ic_bottom_navigation_plane)
        // 이미지 URL 또는 파일 경로를 포함하는 문자열 목록을 생성합니다.
        // 이미지 목록을 list에 추가합니다.

        adapter = PinAdapterGrid(list)
        recyclerView.adapter = adapter
    }
}