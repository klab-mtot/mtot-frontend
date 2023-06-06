package com.example.mtot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mtot.databinding.ActivityJourneyDetailBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.Post
import com.example.mtot.retrofit2.ResponsePhotoUrls
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.ui.post.PinDetailActivity
import com.example.mtot.ui.post.PostDetailActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JourneyDetailActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    lateinit var binding: ActivityJourneyDetailBinding
    var journeyId = -1
    var journeyData: JourneyData? = null
    var arrLoc = ArrayList<LatLng>()
    var pinMarkerId = ArrayList<Int>()
    lateinit var mMap: GoogleMap
    private lateinit var adapter: PinAdapter
    var dataList = ArrayList<String>()
    val editPostLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val retrofitInterface = getRetrofitInterface()
            retrofitInterface.requestJourneyData(journeyId).enqueue(object : Callback<JourneyData> {
                override fun onFailure(call: Call<JourneyData>, t: Throwable) {
                    Log.d("retrofit", t.message.toString())
                }

                override fun onResponse(call: Call<JourneyData>, response: Response<JourneyData>) {
                    Log.d("retrofit", response.body().toString())
                    if (response.isSuccessful) {
                        journeyData = response.body()
                        binding.journeyDetailTitle.text = response.body()!!.name
                        if (journeyData!!.post != null) {
                            binding.journeyDetailPostTitle.text = response.body()!!.post.title
                            binding.journeyDetailPostText.text = response.body()!!.post.article
                        }
                    }
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJourneyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        journeyId = intent.getIntExtra("journeyId", 0)

        binding.journeyDetailPin.layoutManager = GridLayoutManager(this, 3)
        adapter = PinAdapter(this, dataList)
        binding.journeyDetailPin.adapter = adapter

        binding.journeyPostDetailBack.setOnClickListener {
            finish()
        }

        binding.postDetailPostEdit.setOnClickListener {
            val intent = Intent(this, PostDetailActivity::class.java)
            intent.putExtra("journeyId", journeyId)
            editPostLauncher.launch(intent)
        }

        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.requestJourneyData(journeyId).enqueue(object : Callback<JourneyData> {
            override fun onFailure(call: Call<JourneyData>, t: Throwable) {
                Log.d("retrofit", t.message.toString())
            }

            override fun onResponse(call: Call<JourneyData>, response: Response<JourneyData>) {
                Log.d("retrofit", response.body().toString())
                if (response.isSuccessful) {
                    journeyData = response.body()
                    binding.journeyDetailTitle.text = response.body()!!.name
                    if (journeyData!!.post != null) {
                        binding.journeyDetailPostTitle.text = response.body()!!.post.title
                        binding.journeyDetailPostText.text = response.body()!!.post.article
                    }
                    if (journeyData!!.pins.size > 0) {
                        response.body()!!.pins.forEach {
                            arrLoc.add(
                                LatLng(
                                    it.location.latitude,
                                    it.location.longitude
                                )
                            )
                            pinMarkerId.add(it.pinId)
                        }
                    }
                    val mapFragment =
                        supportFragmentManager.findFragmentById(binding.journeyDetailMap.id) as SupportMapFragment
                    mapFragment!!.getMapAsync(this@JourneyDetailActivity)
                }
            }
        })

        //페비
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
                        Glide.with(this@JourneyDetailActivity)
                            .load(photoUrls[0])
                            .into(binding.journeyDetailPostImageView)
                        val photoUrls = photoUrls.map { it }
                        dataList.addAll(photoUrls)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePhotoUrls>, t: Throwable) {
                Log.d("retrofit", t.message.toString())
            }

        })
    }
    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        if (arrLoc.size > 0) {
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(arrLoc!![0], 9.0f)
            )
        } else {
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(40.5, 127.5), 9.0f)
            )
            Toast.makeText(this, "해당 여정에 핀이 없어요! 핀 개수: ${arrLoc.size}", Toast.LENGTH_SHORT).show()
        }

        for (i in 0..arrLoc.size - 1) {
            val option = MarkerOptions()
            option.icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            option.title(pinMarkerId[i].toString())
            option.position(arrLoc[i])
            mMap.addMarker(option)
        }

        mMap.setOnMarkerClickListener(this@JourneyDetailActivity)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val intent = Intent(this@JourneyDetailActivity, PinDetailActivity::class.java)
        intent.putExtra("pinId", marker.title!!.toInt())
        startActivity(intent)
        return true
    }

}

