package com.example.mtot.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.R
import com.example.mtot.databinding.FragmentPostHamburgerBinding

class PostHamburgerFragment : Fragment() {
    lateinit var binding: FragmentPostHamburgerBinding
    lateinit var adapter: PostHamburgerAdapter
    var postHamburgerDataList = ArrayList<PostHamburgerInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostHamburgerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        adapter = PostHamburgerAdapter(postHamburgerDataList)
        binding.rvPostHamburger.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPostHamburger.adapter = adapter


    }

    fun initData(){
        postHamburgerDataList = arrayListOf(
            PostHamburgerInfo(0, "핀1"),
            PostHamburgerInfo(0, "핀2"),
            PostHamburgerInfo(0, "핀3"),
            PostHamburgerInfo(0, "핀4"),
            PostHamburgerInfo(0, "핀5")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postHamburgerDataList.clear()
    }
}