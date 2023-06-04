package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mtot.databinding.ActivityPinListBinding
import com.example.mtot.retrofit2.PhotoUrls
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinListActivity : AppCompatActivity() {
    lateinit var binding: ActivityPinListBinding
    private lateinit var adapter: PinAdapter
    var dataList = ArrayList<PinData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPinListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val journeyId = intent.getStringExtra("journeyId")

        if (journeyId != null) {
            initData(journeyId)
        }

        binding.pinRecyclerView.layoutManager = GridLayoutManager(this, 3)

        adapter = PinAdapter(dataList)
        binding.pinRecyclerView.adapter = adapter
    }

    fun initData(journeyId: String) {
        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.getJourneyPhotos(journeyId).enqueue(object : Callback<List<PhotoUrls>> {
            override fun onResponse(call: Call<List<PhotoUrls>>, response: Response<List<PhotoUrls>>) {
                if (response.isSuccessful) {
                    Log.d("hello", response.body().toString())
                    val photoUrlsList = response.body()
                    if (photoUrlsList != null) {
                        val photoUrls = photoUrlsList.map { it.url }
                        dataList.addAll(photoUrls.map { PinData(it) })
                        adapter.notifyDataSetChanged()
                        // Notify the adapter of the data change if needed
                    }
                }
            }

            override fun onFailure(call: Call<List<PhotoUrls>>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

        })
    }
}