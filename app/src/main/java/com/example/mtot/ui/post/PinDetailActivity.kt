package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mtot.PinAdapter
import com.example.mtot.databinding.ActivityPinDetailBinding
import com.example.mtot.retrofit2.PhotoData
import com.example.mtot.retrofit2.RequestPhotos
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPinDetailBinding
    private lateinit var adapter: PinAdapter
    var dataList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pinId = intent.getIntExtra("pinId", -1)
        adapter = PinAdapter(this, dataList)
        binding.pinRecyclerView.layoutManager = GridLayoutManager(this, 3)
        initData(pinId)

        binding.buttonPinDetailBack.setOnClickListener {
            finish()
        }

        binding.pinRecyclerView.adapter = adapter
    }

    fun initData(pinId: Int) {
        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.requestPinPhotos(pinId).enqueue(object : Callback<RequestPhotos> {
            override fun onResponse(
                call: Call<RequestPhotos>,
                response: Response<RequestPhotos>
            ) {
                Log.d("hello", response.body().toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    val photoUrls = responseBody.photoUrls
                    if (photoUrls.isNotEmpty()) {
                        val photoUrls = photoUrls.map { it }
                        dataList.addAll(photoUrls)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<RequestPhotos>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

        })
    }
}
