package com.example.mtot.ui.social

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.databinding.ActivityFriendRequestBinding
import com.example.mtot.retrofit2.PendingFriendshipsData
import com.example.mtot.retrofit2.ResponseFriendRequestData
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendRequestActivity : AppCompatActivity() {
    lateinit var binding: ActivityFriendRequestBinding
    lateinit var adapter: FriendRequestAdapter
    var dataList = ArrayList<FriendRequestListInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    fun initLayout() {

        val friendRequestInterface = getRetrofitInterface()
        Log.d("QQQ","WWW")
        friendRequestInterface.getPendingFriendships()
            .enqueue(object : Callback<PendingFriendshipsData> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<PendingFriendshipsData>,
                    response: Response<PendingFriendshipsData>
                ) {
                    Log.d("HHHH", response.body().toString())
                    if (response.isSuccessful) {
                        response.body()!!.pendingFriendships.forEach {
                            dataList.add(FriendRequestListInfo(it.friendshipId, it.requesterName))
                        }
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<PendingFriendshipsData>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }
            })

        adapter = FriendRequestAdapter(dataList) // 데이터 리스트를 어댑터에 전달합니다
        binding.friendRequestNameList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.OnItemClickListener = object : FriendRequestAdapter.onItemClickListener {
            override fun onAcceptButtonClicked(position: Int) {
                friendRequestInterface.acceptPendingFriendship(dataList[position].friendshipId)
                    .enqueue(object : Callback<ResponseFriendRequestData> {
                        override fun onResponse(
                            call: Call<ResponseFriendRequestData>,
                            response: Response<ResponseFriendRequestData>
                        ) {
                            Log.d("HHH", response.body().toString())
                            if (response.isSuccessful) {
                                adapter.removeItem(position)
                                Log.d("jin", adapter.items.toString())
                            }
                        }

                        override fun onFailure(call: Call<ResponseFriendRequestData>, t: Throwable) {
                            Log.d("QQQ", t.message.toString())
                        }
                    })
            }

            override fun onRejectButtonClicked(position: Int) {
                friendRequestInterface.rejectPendingFriendship(dataList[position].friendshipId)
                    .enqueue(object : Callback<ResponseFriendRequestData> {
                        override fun onResponse(call: Call<ResponseFriendRequestData>, response: Response<ResponseFriendRequestData>) {
                            Log.d("HHH", response.body().toString())
                            if (response.isSuccessful) {
                                adapter.removeItem(position)
                            }
                        }

                        override fun onFailure(call: Call<ResponseFriendRequestData>, t: Throwable) {
                            Log.d("QQQ", t.message.toString())
                        }
                    })

            }
        }
        binding.friendRequestNameList.adapter = adapter

        binding.friendRequestBack.setOnClickListener {
            finish()
        }

        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }

    }
}

