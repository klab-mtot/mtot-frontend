package com.example.mtot.ui.map

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mtot.MainActivity
import com.example.mtot.R
import com.example.mtot.databinding.FragmentMapBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.JourneysData
import com.example.mtot.retrofit2.getRetrofitInterface
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentMapBinding
    lateinit var googleMap: GoogleMap

    var arrLoc = ArrayList<LatLng>()
    lateinit var journeysData: JourneysData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        initMap()

        binding.cvMapHamburgerButton.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.showMapHamburgerToolbar()
        }

        return binding.root
    }

    private fun initMap() {
        val journeyInterface = getRetrofitInterface()

        journeyInterface.requestJourneyData().enqueue(object : Callback<JourneysData> {
            override fun onFailure(call: Call<JourneysData>, t: Throwable) {
                Log.d("Hello", t.message.toString())
            }

            override fun onResponse(
                call: Call<JourneysData>,
                response: Response<JourneysData>
            ) {
                Log.d("Hello", response.body().toString())
                if (response.isSuccessful) {
                    journeysData = response.body()!!
                    journeysData.journeys.forEach {
                        arrLoc.add(
                            LatLng(
                                it.pins[0].location.latitude,
                                it.pins[0].location.longitude
                            )
                        )
                    }
                }
            }
        })

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@MapFragment)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        if(!arrLoc.isEmpty()){
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arrLoc[0], 8.0f))
        }
        else {
            googleMap.addMarker(MarkerOptions().position(LatLng(36.5000,127.5000)))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(36.5000,127.5000) , 8.0f))
        }
        googleMap.setMinZoomPreference(5.0f)
        googleMap.setMaxZoomPreference(20.0f)

        val option = MarkerOptions()
        option.icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )
        arrLoc.forEach {
        }

        googleMap.setOnMapClickListener { loc ->
            arrLoc.add(loc)
            val option2 = MarkerOptions()
            option2.position(loc)
            option2.icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            googleMap.addMarker(option2)
            val option3 = PolylineOptions().color(Color.BLUE).addAll(arrLoc)
            googleMap.addPolyline(option3)
        }
    }
}
