package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mtot.R
import com.example.mtot.databinding.ActivityPostDetailBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.JourneysData
import com.example.mtot.retrofit2.PhotoData
import com.example.mtot.retrofit2.Post
import com.example.mtot.retrofit2.getRetrofitInterface
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PostDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostDetailBinding
    var journeyPhoto: String? = null
    var journeyPost: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    fun initLayout() {
        var journeyId = 0

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
                        Glide.with(this@PostDetailActivity)
                            .load(journeyPhoto)
                            .into(binding!!.postDetailImageView)
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
                    binding!!.postTitle.text = journeyPost!!.title
                    binding!!.postText.text = journeyPost!!.article
                }
            }
        })


    }
}