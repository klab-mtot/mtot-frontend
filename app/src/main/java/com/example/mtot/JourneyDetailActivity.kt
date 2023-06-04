package com.example.mtot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.mtot.databinding.ActivityJourneyDetailBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.PhotoData
import com.example.mtot.retrofit2.PhotoUrls
import com.example.mtot.retrofit2.Post
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.ui.post.PinAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JourneyDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityJourneyDetailBinding
    var journeyPhoto: String? = null
    var journeyPost: Post? = null

    private lateinit var adapter: PinAdapter
    var dataList = ArrayList<PhotoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJourneyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()

    }

    fun initLayout() {
        val journeyId = intent.getStringExtra("journeyId")

        var journeyInterface = getRetrofitInterface()

        journeyInterface.requestJourneyPhotos()
            .enqueue(object : retrofit2.Callback<List<PhotoData>> {
                override fun onFailure(call: Call<List<PhotoData>>, t: Throwable) {
                    Log.d("HELLO", t.message.toString())
                }

                override fun onResponse(
                    call: Call<List<PhotoData>>,
                    response: Response<List<PhotoData>>
                ) {
                    Log.d("HELLO", response.body().toString())
                    if (response.isSuccessful) {
                        var journeyPhotos = response.body()!!
                        journeyPhoto = journeyPhotos[0].toString()
                        Glide.with(this@JourneyDetailActivity)
                            .load(journeyPhoto)
                            .into(binding!!.journeyDetailPostImageView)
                    }
                }
            })


        journeyInterface.requestJourneyData().enqueue(object : retrofit2.Callback<JourneyData> {
            override fun onFailure(call: Call<JourneyData>, t: Throwable) {
                Log.d("HELLO", t.message.toString())
            }

            override fun onResponse(call: Call<JourneyData>, response: Response<JourneyData>) {
                Log.d("HELLO", response.body().toString())
                if (response.isSuccessful) {
                    journeyPost = response.body()!!.post
                    binding!!.journeyDetailPostText.text = journeyPost!!.title
                    binding!!.journeyDetailPostText.text = journeyPost!!.article
                    binding!!.journeyDetailTitle.text = response.body()!!.name
                }
            }
        })



        //페비 핀 사진 넣는 코드
//        val journeyId = intent.getStringExtra("journeyId")

        binding.journeyDetailPin.layoutManager = GridLayoutManager(this, 3)
        adapter = PinAdapter(dataList)

        if (journeyId != null) {
            initData(journeyId)
        }

        binding.journeyDetailPin.adapter = adapter


    }

    fun initData(journeyId: String) {
        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.getJourneyPhotos(journeyId).enqueue(object : Callback<List<PhotoUrls>> {
            override fun onResponse(
                call: Call<List<PhotoUrls>>,
                response: Response<List<PhotoUrls>>
            ) {
                if (response.isSuccessful) {
                    Log.d("hello", response.body().toString())
                    val photoUrlsList = response.body()
                    if (photoUrlsList != null) {
                        val photoUrls = photoUrlsList.map { it.url }
                        dataList.addAll(photoUrls.map { PhotoData(it) })
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
