package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mtot.R
import com.example.mtot.databinding.ActivityPostDetailBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.JourneysData
import com.example.mtot.retrofit2.PhotoData
import com.example.mtot.retrofit2.PhotoUrls
import com.example.mtot.retrofit2.Post
import com.example.mtot.retrofit2.getJourneyId
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
        //val journeyId = intent.getIntExtra("journeyId")
        val journeyId = 0

        if (journeyId != null) {
            initData(journeyId)
        }

    }
    fun initData(journeyId: Int) {


        var journeyInterface = getRetrofitInterface()



        journeyInterface.requestJourneyData(journeyId).enqueue(object :
            retrofit2.Callback<JourneyData> {
            override fun onFailure(call: Call<JourneyData>, t: Throwable) {
                Log.d("HELLO", t.message.toString())
            }

            override fun onResponse(call: Call<JourneyData>, response: Response<JourneyData>) {
                Log.d("HELLO", response.body().toString())
                if (response.isSuccessful) {
                    binding.postTitle .text = response.body()!!.post.title
                    binding.postText.text = response.body()!!.post.article
                }
            }
        })

        //페비
        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.getJourneyPhotos(journeyId).enqueue(object :
            retrofit2.Callback<List<PhotoUrls>> {
            override fun onResponse(
                call: Call<List<PhotoUrls>>,
                response: Response<List<PhotoUrls>>
            ) {
                Log.d("hello", response.body().toString())
                if (response.isSuccessful) {
                    val photoUrlsList = response.body()
                    if (photoUrlsList != null) {
                        Glide.with(this@PostDetailActivity)
                            .load(photoUrlsList[0])
                            .into(binding.postDetailImageView)
                    }
                }
            }

            override fun onFailure(call: Call<List<PhotoUrls>>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

        })
    }

}