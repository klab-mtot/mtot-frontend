package com.example.mtot.ui.social

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.FriendRequestActivity
import com.example.mtot.databinding.FragmentSocialBinding
import com.example.mtot.retrofit2.FriendsData
import com.example.mtot.retrofit2.GetTeamsResponse
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        groupAdapter = GroupListAdapter()
        binding.rvSocialGrouplist.adapter = groupAdapter

        binding.rvSocialFriendlist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        friendAdapter = FriendListAdapter()
        binding.rvSocialFriendlist.adapter = friendAdapter

        binding.ivSocialGrouplist.setOnClickListener {
            val i = Intent(requireContext(), AddGroupActivity::class.java)
            startActivity(i)
        }

        binding.ivSocialFriendlist.setOnClickListener {
            val i = Intent(requireContext(), AddFriendActivity::class.java)
            startActivity(i)
        }

        binding.friendRequestListButton.setOnClickListener {
            val i=Intent(requireContext(), FriendRequestActivity::class.java)
            startActivity(i)
        }


        return binding.root
    }

    fun initData() {
        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.getTeams().enqueue(object : Callback<GetTeamsResponse> {
            override fun onFailure(call: Call<GetTeamsResponse>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

            override fun onResponse(call: Call<GetTeamsResponse>, response: Response<GetTeamsResponse>) {
                Log.d("hello", response.body().toString())
                if(response.isSuccessful){
                    groupDataList = ArrayList<SocialListInfo>()
                    val list = response.body()!!
                    groupDataList.addAll(list.teamList.map{
                        SocialListInfo(0, 0, it.teamName)
                    }.toList())
                    groupAdapter.notifyDataSetChanged()
                }
            }
        })

        retrofitInterface.requestFriendsData().enqueue(object : Callback<FriendsData> {
            override fun onFailure(call: Call<FriendsData>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

            override fun onResponse(call: Call<FriendsData>, response: Response<FriendsData>) {
                Log.d("hello", response.body().toString())
                if(response.isSuccessful){
                    friendDataList = ArrayList<SocialListInfo>()
                    friendDataList.addAll(response.body()!!.friendships.map {
                        SocialListInfo(0, 0, it.name)
                    }.toList())
                    friendAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}