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
import com.example.mtot.retrofit2.FriendData
import com.example.mtot.retrofit2.getRetrofitInterface

class SocialFragment : Fragment() {
    lateinit var binding: FragmentSocialBinding
    lateinit var groupAdapter: GroupListAdapter
    lateinit var friendAdapter: FriendListAdapter
    lateinit var groupDataList: ArrayList<SocialListInfo>
    lateinit var friendDataList: ArrayList<SocialListInfo>
    //서버에서 받아온 frinedData형식
    var friendData:FriendData?=null



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
            val i = Intent(requireContext(), AddGroupActivity::class.java)
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



        val friendInterface = getRetrofitInterface()

//        friendInterface.requestFriendData().enqueue(object : Callback<FriendData> {
//            override fun onFailure(call: Call<FriendData>, t: Throwable) {
//                t.message?.let { it1 -> Log.e("FRIEND", it1) }
//                val dialog = AlertDialog.Builder(requireContext())
//                dialog.setTitle("에러")
//                dialog.setMessage("호출실패했습니다.")
//                dialog.create().show()
//            }
//
//            override fun onResponse(call: Call<FriendData>, response: Response<FriendData>) {
//                //friendData를 여러개 가져오면 어떻게 되지?
//                friendData = response.body()
//                //sharedPreference에 저장하려면 String으로 바꿔야 함.
//                saveFriendData(requireContext(), response.body().toString())
//                Log.d("FRIEND", "friendshipId : " + friendData?.friendshipId)
//                Log.d("FRIEND", "memberId : " + friendData?.memberId)
//                Log.d("FRIEND", "name : " + friendData?.name)
//                Log.d("FRIEND", "email : " + friendData?.email)
//                val dialog = AlertDialog.Builder(requireContext())
//                dialog.setTitle(friendData?.name)
//                dialog.setMessage(friendData?.memberId.toString())
//                dialog.create().show()
//            }
//        })


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