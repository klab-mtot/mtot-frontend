package com.example.mtot.ui.social

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
    var groupDataList = ArrayList<SocialListInfo>()
    var friendDataList = ArrayList<SocialListInfo>()
    val friendAddLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val retrofitInterface = getRetrofitInterface()
            retrofitInterface.requestFriendsData().enqueue(object : Callback<FriendsData> {
                override fun onFailure(call: Call<FriendsData>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }

                override fun onResponse(call: Call<FriendsData>, response: Response<FriendsData>) {
                    Log.d("hello", response.body().toString())
                    if (response.isSuccessful) {
                        friendDataList.clear()
                        friendDataList.addAll(response.body()!!.friendships.map {
                            SocialListInfo(it.friendshipId, 0, it.name)
                        }.toList())
                        friendAdapter.notifyDataSetChanged()
                    }

                }
            })
        }

    val groupAddLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val retrofitInterface = getRetrofitInterface()
            retrofitInterface.getTeams().enqueue(object : Callback<GetTeamsResponse> {
                override fun onFailure(call: Call<GetTeamsResponse>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }

                override fun onResponse(
                    call: Call<GetTeamsResponse>,
                    response: Response<GetTeamsResponse>
                ) {
                    Log.d("hello", response.body().toString())
                    if (response.isSuccessful) {
                        groupDataList.clear()
                        val list = response.body()!!
                        groupDataList.addAll(list.teamList.map {
                            SocialListInfo(it.teamId, 0, it.teamName)
                        }.toList())
                        groupAdapter.notifyDataSetChanged()
                    }
                }
            })
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()

        binding.rvSocialGrouplist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        groupAdapter = GroupListAdapter(groupDataList)
        groupAdapter.OnItemClickListener = object : GroupListAdapter.onItemClickListener {
            override fun onItemClicked(position: Int) {
                val i = Intent(requireContext(), GroupDetailActivity::class.java)
                i.putExtra("teamId", groupAdapter.items[position].id)
                startActivity(i)
            }
        }
        binding.rvSocialGrouplist.adapter = groupAdapter

        binding.rvSocialFriendlist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        friendAdapter = FriendListAdapter(friendDataList)
        binding.rvSocialFriendlist.adapter = friendAdapter

        binding.ivSocialGrouplist.setOnClickListener {
            val i = Intent(requireContext(), AddGroupActivity::class.java)
            groupAddLauncher.launch(i)
        }

        binding.ivSocialFriendlist.setOnClickListener {
            val i = Intent(requireContext(), AddFriendActivity::class.java)
            friendAddLauncher.launch(i)
        }

        binding.friendRequestListButton.setOnClickListener {
            val i = Intent(requireContext(), FriendRequestActivity::class.java)
            friendAddLauncher.launch(i)
        }

    }

    fun initData() {
        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.getTeams().enqueue(object : Callback<GetTeamsResponse> {
            override fun onFailure(call: Call<GetTeamsResponse>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

            override fun onResponse(
                call: Call<GetTeamsResponse>,
                response: Response<GetTeamsResponse>
            ) {
                Log.d("hello", response.body().toString())
                if (response.isSuccessful) {
                    val list = response.body()!!
                    groupDataList.addAll(list.teamList.map {
                        SocialListInfo(it.teamId, 0, it.teamName)
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
                if (response.isSuccessful) {
                    friendDataList.addAll(response.body()!!.friendships.map {
                        SocialListInfo(it.friendshipId, 0, it.name)
                    }.toList())
                    friendAdapter.notifyDataSetChanged()
                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        groupDataList.clear()
        friendDataList.clear()
    }
}