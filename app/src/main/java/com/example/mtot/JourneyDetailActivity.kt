package com.example.mtot

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mtot.databinding.ActivityJourneyDetailBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.PhotoData
import com.example.mtot.retrofit2.PhotoUrls
import com.example.mtot.retrofit2.Post
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.ui.post.PinAdapter
import com.example.mtot.ui.post.PinDetailActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JourneyDetailActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    lateinit var binding: ActivityJourneyDetailBinding
    var journeyPhoto: String? = null
    var journeyPost: Post? = null
    var journeyData: JourneyData? = null
    var arrLoc = ArrayList<LatLng>()

    lateinit var mMap: GoogleMap

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
        val journeyId = intent.getIntExtra("journeyId", 0)

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
                        }
                    }
                    val mapFragment =
                        supportFragmentManager.findFragmentById(binding.journeyDetailMap.id) as SupportMapFragment?
                    mapFragment!!.getMapAsync(this@JourneyDetailActivity)



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

        binding.journeyPostDetailBack.setOnClickListener {
            finish()
        }

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
        val markerOptions = MarkerOptions()
        val polylineOption = PolylineOptions().color(Color.BLUE).addAll(arrLoc)
        mMap.setOnMarkerClickListener(this@JourneyDetailActivity)


        arrLoc!!.forEach {
            markerOptions.position(it)
            mMap.addMarker(markerOptions)
            mMap.addPolyline(polylineOption)
        }

    }


    //아래를 핀 상세로 가는걸로 변경
    override fun onMarkerClick(marker: Marker): Boolean {
        val intent= Intent(this@JourneyDetailActivity, PinDetailActivity::class.java)
        intent.putExtra("pinId", marker.id)
        startActivity(intent)
        return true
    }

}
