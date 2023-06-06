package com.example.mtot.ui.post

import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mtot.R
import com.example.mtot.databinding.ActivityPostDetailBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.JourneysData
import com.example.mtot.retrofit2.PhotoData
import com.example.mtot.retrofit2.PhotoUrls
import com.example.mtot.retrofit2.Post
import com.example.mtot.retrofit2.ResponseAddPost
import com.example.mtot.retrofit2.ResponsePhotoUrls
import com.example.mtot.retrofit2.getRetrofitInterface
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostDetailBinding
    var title = ""
    var article = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val journeyId = intent.getIntExtra("journeyId", -1)
        val retrofitInterface = getRetrofitInterface()
        retrofitInterface.requestJourneyData(journeyId).enqueue(object: Callback<JourneyData>{
            override fun onResponse(call: Call<JourneyData>, response: Response<JourneyData>) {
                Log.d("retrofit", response.body().toString())
                if(response.isSuccessful){
                    val journeyData = response.body()!!
                    title = journeyData.post.title
                    article = journeyData.post.article
                    binding.postTitle1.text = title
                    binding.postText1.text = article
                }
            }

            override fun onFailure(call: Call<JourneyData>, t: Throwable) {
                Log.d("retrofit", t.message.toString())
            }

        })

        retrofitInterface.getJourneyPhotos(journeyId).enqueue(object : Callback<ResponsePhotoUrls> {
            override fun onResponse(
                call: Call<ResponsePhotoUrls>,
                response: Response<ResponsePhotoUrls>
            ) {
                Log.d("retrofit", response.body().toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    val photoUrls = responseBody.photoUrls
                    if (photoUrls.isNotEmpty()) {
                        Glide.with(this@PostDetailActivity)
                            .load(photoUrls[0])
                            .into(binding.postDetailImageView1)
                        Glide.with(this@PostDetailActivity)
                            .load(photoUrls[0])
                            .into(binding.postDetailImageView2)
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePhotoUrls>, t: Throwable) {
                Log.d("retrofit", t.message.toString())
            }

        })

        binding.postStart.setOnClickListener {
            binding.postDetailEdit2.visibility = View.VISIBLE
            binding.postDetailEdit1.visibility = View.GONE

            binding.postTitle2.setText(binding.postTitle1.text.toString())
            binding.postText2.setText(binding.postText1.text.toString())
        }

        binding.postFinish.setOnClickListener {
            binding.postDetailEdit2.visibility = View.GONE
            binding.postDetailEdit1.visibility = View.VISIBLE

            binding.postTitle1.setText(binding.postTitle2.text.toString())
            binding.postText1.setText(binding.postText2.text.toString())
        }

        binding.postBack1.setOnClickListener {
            retrofitInterface.editPost(
                Post(journeyId,binding.postTitle1.text.toString(), binding.postText1.text.toString())
            ).enqueue(object: Callback<ResponseAddPost>{
                override fun onResponse(
                    call: Call<ResponseAddPost>,
                    response: Response<ResponseAddPost>
                ) {
                    Log.d("retrofit", response.body().toString())
                    if(response.isSuccessful)
                        finish()
                }

                override fun onFailure(call: Call<ResponseAddPost>, t: Throwable) {
                    Log.d("retrofit", t.message.toString())
                }
            })
        }
    }
}