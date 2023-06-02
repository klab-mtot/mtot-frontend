package com.example.mtot.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.HamburgerItemInfo
import com.example.mtot.R
import com.example.mtot.databinding.FragmentMapHamburgerBinding
import com.example.mtot.ui.post.MapHamburgerAdapter
import com.example.mtot.ui.post.PostHamburgerAdapter

class MapHamburgerFragment : Fragment() {
    lateinit var binding : FragmentMapHamburgerBinding
    lateinit var adapter: MapHamburgerAdapter
    var mapHamburgerDataList = ArrayList<HamburgerItemInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapHamburgerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        adapter = MapHamburgerAdapter(mapHamburgerDataList)
        binding.rvMapHamburger.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMapHamburger.adapter = adapter
    }

    fun initData(){
        mapHamburgerDataList = arrayListOf(
            HamburgerItemInfo(0, "여정1"),
            HamburgerItemInfo(0, "여정2"),
            HamburgerItemInfo(0, "여정3"),
            HamburgerItemInfo(0, "여정4"),
            HamburgerItemInfo(0, "여정5")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapHamburgerDataList.clear()
    }

}