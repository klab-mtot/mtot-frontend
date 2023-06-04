package com.example.mtot

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.mtot.databinding.ActivityJourneyDetailBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.JourneysData
import com.example.mtot.retrofit2.PhotoData
import com.example.mtot.retrofit2.PhotoUrls
import com.example.mtot.retrofit2.Post
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.ui.map.MapFragment
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
        //정진우-아예 연결할 때 저니 데이터를 줘서 찍어주도록 해야될 듯.
        //아니면 짤 때 Id를 받아서 하도록 해야 하는데 그건 어케했지.. 페비는 어케했지.. 봐야겠다.
        val journeyId = intent.getIntExtra("journeyId",0)

        binding.journeyDetailPin.layoutManager = GridLayoutManager(this, 3)
        adapter = PinAdapter(dataList)

        if (journeyId != null) {
            initData(journeyId)
        }

        binding.journeyDetailPin.adapter = adapter

    }

    fun initData(journeyId: Int) {


        var journeyInterface = getRetrofitInterface()



        journeyInterface.requestJourneyData(journeyId).enqueue(object : Callback<JourneyData> {
            override fun onFailure(call: Call<JourneyData>, t: Throwable) {
                Log.d("HELLO", t.message.toString())
            }

            override fun onResponse(call: Call<JourneyData>, response: Response<JourneyData>) {
                Log.d("HELLO", response.body().toString())
                if (response.isSuccessful) {
                    binding.journeyDetailTitle.text = response.body()!!.name
                    binding.journeyDetailPostTitle.text = response.body()!!.post.title
                    binding.journeyDetailPostText.text = response.body()!!.post.article
                }
            }
        })

        //페비
        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.getJourneyPhotos(journeyId).enqueue(object : Callback<List<PhotoUrls>> {
            override fun onResponse(
                call: Call<List<PhotoUrls>>,
                response: Response<List<PhotoUrls>>
            ) {
                Log.d("hello", response.body().toString())
                if (response.isSuccessful) {
                    val photoUrlsList = response.body()
                    if (photoUrlsList != null) {
                        Glide.with(this@JourneyDetailActivity)
                            .load(photoUrlsList[0])
                            .into(binding.journeyDetailPostImageView)
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
