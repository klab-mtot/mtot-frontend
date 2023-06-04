package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mtot.databinding.ActivityPinDetailBinding
import com.example.mtot.retrofit2.PhotoData
import com.example.mtot.retrofit2.PhotoUrls
import com.example.mtot.retrofit2.RequestPhotos
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPinDetailBinding
    private lateinit var adapter: PinAdapter
    var dataList = ArrayList<PhotoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pinId = intent.getIntExtra("pinId", -1)
        adapter = PinAdapter(dataList)
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
                if (response.isSuccessful) {
                    Log.d("hello", response.body().toString())
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val photoUrls = responseBody.photoUrls.map { it.url }
                        dataList.addAll(photoUrls.map { PhotoData(it) })
                        Log.d("hello", photoUrls.toString())
                        adapter.notifyDataSetChanged()
                        // Notify the adapter of the data change if needed
                    }
                }
            }

            override fun onFailure(call: Call<RequestPhotos>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

        })
    }
}