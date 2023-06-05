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
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getRetrofitInterface
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val journeyId = intent.getIntExtra("journeyId", -1)

        if (journeyId != -1) {
            initData(journeyId)
        }



        binding.postStart.setOnClickListener {
            binding.postDetailEdit1.visibility = View.GONE
            binding.postDetailEdit2.visibility = View.VISIBLE
            binding.postBack2.visibility=View.INVISIBLE

            binding.postTitle2.setText(binding.postTitle1.text)
            binding.postText2.setText(binding.postText1.text)
        }
        binding.postFinish.setOnClickListener {
            binding.postDetailEdit2.visibility = View.GONE
            binding.postDetailEdit1.visibility = View.VISIBLE

            binding.postTitle1.text = binding.postTitle2.text
            binding.postText1.text = binding.postText2.text
        }

        binding.postBack1.setOnClickListener {
            //POST로 저장하기


            finish()
        }

    }

    fun initData(journeyId: Int) {


        var postInterface = getRetrofitInterface()

        //찍어주기
        postInterface.requestJourneyData(journeyId).enqueue(object : Callback<JourneyData> {

            override fun onResponse(call: Call<JourneyData>, response: Response<JourneyData>) {
                Log.d("HI", "HI")
                if (response.isSuccessful) {
                    binding.postDetailEdit2.visibility=View.GONE
                    binding.postTitle1.text = response.body()!!.post.title
                    binding.postText1.text = response.body()!!.post.article
                    binding.postTitle2.setText(binding.postTitle1.text)
                    binding.postText2.setText(binding.postText1.text)
                }
            }

            override fun onFailure(call: Call<JourneyData>, t: Throwable) {
                Log.d("HI", t.message.toString())
            }
        })
    }


}