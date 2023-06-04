package com.example.mtot

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide.init
import com.example.mtot.databinding.ActivityFriendRequestBinding
import com.example.mtot.databinding.ItemFriendRequestListRowBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.PendingFriendshipsData
import com.example.mtot.retrofit2.RetrofitInterface
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.ui.social.FriendListAdapter
import com.example.mtot.ui.social.FriendRequestAdapter
import com.example.mtot.ui.social.FriendRequestListInfo
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
                    .enqueue(object : Callback<String> {
                        override fun onResponse(
                            call: Call<String>,
                            response: Response<String>
                        ) {
                            Log.d("HHH", response.body().toString())
                            if (response.isSuccessful) {
                                remove(position)
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.d("QQQ", t.message.toString())
                        }
                    })
            }

            override fun onRejectButtonClicked(position: Int) {
                friendRequestInterface.rejectPendingFriendship(dataList[position].friendshipId)
                    .enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            Log.d("HHH", response.body().toString())
                            if (response.isSuccessful) {
                                remove(position)
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.d("QQQ", t.message.toString())
                        }
                    })

            }
        }

        binding.friendRequestNameList.adapter = adapter


    }

    fun remove(position:Int) {
        dataList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}

