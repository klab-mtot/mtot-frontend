package com.example.mtot.ui.post

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mtot.R
import com.example.mtot.databinding.FragmentPostBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class PostFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentPostBinding
    lateinit var googleMap: GoogleMap
    var loc= LatLng(37.554752, 126.970631)
    lateinit var myLoc : LatLng
    val arrLoc=ArrayList<LatLng>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)

        val mapFragment= childFragmentManager.findFragmentById(R.id.fcv_post_map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        return binding.root
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap=p0
        googleMap.mapType=GoogleMap.MAP_TYPE_NORMAL
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
        googleMap.setMinZoomPreference(10.0f)
        googleMap.setMaxZoomPreference(20.0f)
        val option= MarkerOptions()
        option.position(loc)
        option.icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )
        option.title("핀")
        option.snippet("지역 이름")
        googleMap.addMarker(option)?.showInfoWindow()

        /*
            밑의 addMark와 함께 맵에다가 클릭한 위치를 기억해두고
            bottom navigation에서 floating action button click 시
            mark 추가하는 식으로 구성함
            gps 기능 추가 나중에 하려고..
         */
        googleMap.setOnMapClickListener{
            myLoc = it
        }
    }

    fun addMark(){
        arrLoc.add(myLoc)
        val option2= MarkerOptions()
        option2.position(myLoc)
        option2.icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )
        googleMap.addMarker(option2)
        val option3= PolylineOptions().color(Color.BLUE).addAll(arrLoc)
        googleMap.addPolyline(option3)
    }
}