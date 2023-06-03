package com.example.mtot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.mtot.databinding.ActivityFriendRequestBinding
import com.example.mtot.databinding.ItemFriendRequestListRowBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.PendingFriendshipsData
import com.example.mtot.retrofit2.RetrofitInterface
import com.example.mtot.retrofit2.getRetrofitInterface
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
        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.getPendingFriendships()
            .enqueue(object : Callback<PendingFriendshipsData> {
                override fun onResponse(
                    call: Call<PendingFriendshipsData>,
                    response: Response<PendingFriendshipsData>
                ) {
                    Log.d("hello", response.body().toString())
                    if (response.isSuccessful) {
                        var temp = response.body()!!.pendingFriendships.forEach {
                            dataList.add(FriendRequestListInfo(it.friendshipId, it.requesterName))
                        }
                    }
                }

                override fun onFailure(call: Call<PendingFriendshipsData>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }
            })

        adapter = FriendRequestAdapter(dataList) // 데이터 리스트를 어댑터에 전달합니다
        binding.friendRequestNameList.adapter = adapter

    }
}
