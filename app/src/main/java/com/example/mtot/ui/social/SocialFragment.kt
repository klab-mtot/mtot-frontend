package com.example.mtot.ui.social

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.R
import com.example.mtot.databinding.FragmentSocialBinding

class SocialFragment : Fragment() {
    lateinit var binding: FragmentSocialBinding
    lateinit var groupAdapter: GroupListAdapter
    lateinit var friendAdapter: FriendListAdapter
    lateinit var groupDataList: ArrayList<SocialListInfo>
    lateinit var friendDataList: ArrayList<SocialListInfo>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSocialBinding.inflate(inflater, container, false)

        initData()

        binding.rvSocialGrouplist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        groupAdapter = GroupListAdapter(groupDataList)
        binding.rvSocialGrouplist.adapter = groupAdapter

        binding.rvSocialFriendlist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        friendAdapter = FriendListAdapter(friendDataList)
        binding.rvSocialFriendlist.adapter = friendAdapter

        binding.ivSocialGrouplist.setOnClickListener {
            val i = Intent(requireContext(), AddFriendActivity::class.java)
            startActivity(i)
        }

        binding.ivSocialFriendlist.setOnClickListener {
            val i = Intent(requireContext(), AddFriendActivity::class.java)
            startActivity(i)
        }


        return binding.root
    }

    fun initData() {
        groupDataList = arrayListOf(
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹1"),
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹2"),
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹3"),
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹4"),
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹5"),
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹6"),
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹7"),
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹8"),
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹9"),
            SocialListInfo(0, R.drawable.ic_bottom_navigation_calendar, "그룹10")
        )


        friendDataList = arrayListOf(
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구1"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구2"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구3"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구4"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구5"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구6"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구7"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구8"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구9"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구10"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구11"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구12"),
            SocialListInfo(1, R.drawable.ic_bottom_navigation_calendar, "친구13")
        )
    }
}