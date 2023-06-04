package com.example.mtot.ui.post

import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getRetrofitInterface
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
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

        binding.postEdit.setOnClickListener {
            if(binding.postTitle.isVisible){
                binding.postTitle.isVisible=false
                binding.postText.isVisible=false

                binding.postTitleEdit.isVisible=true
                binding.postTextEdit.isVisible=true

                binding.postTitleEdit.setText(binding.postTitle.text.toString())
                binding.postTextEdit.setText(binding.postText.text.toString())

            }
            else{
                binding.postTitleEdit.isVisible=false
                binding.postTextEdit.isVisible=false

                binding.postTitle.isVisible=true
                binding.postText.isVisible=true

                binding.postTitle.setText(binding.postTitleEdit.text.toString())
                binding.postText.setText(binding.postTextEdit.text.toString())

            }

        }

    }

    fun initData(journeyId: Int) {


        var journeyInterface = getRetrofitInterface()

        binding.postBack.setOnClickListener {
            if(binding.postTitle.isVisible){
//                journeyInterface.EditPost(journeyId,binding.postTitle.toString(), binding.postText.toString())
//                    .enqueue()
                finish()
            }
            else{
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("게시글 수정 중")
                dialogBuilder.setMessage("게시글 수정을 마치셔야 합니다")
                dialogBuilder.setPositiveButton("OK") { dialogInterface: DialogInterface, _: Int ->
                    // OK 버튼을 클릭했을 때 수행할 동작
                    dialogInterface.dismiss() // 다이얼로그를 닫습니다.
                }
                dialogBuilder.show()
            }
        }



        journeyInterface.requestJourneyData(journeyId).enqueue(object :
            retrofit2.Callback<JourneyData> {
            override fun onFailure(call: Call<JourneyData>, t: Throwable) {
                Log.d("HELLO", t.message.toString())
            }

            override fun onResponse(call: Call<JourneyData>, response: Response<JourneyData>) {
                Log.d("HELLO", response.body().toString())
                if (response.isSuccessful && response.body()!!.post !=null) {
                    binding.postTitle.text = response.body()!!.post.title
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