package com.example.mtot.ui.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mtot.HamburgerItemInfo
import com.example.mtot.MainActivity
import com.example.mtot.R
import com.example.mtot.databinding.FragmentCalendarBinding
import com.example.mtot.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapFragment : Fragment() , OnMapReadyCallback{

    lateinit var binding: FragmentMapBinding
    lateinit var googleMap:GoogleMap
    var loc=LatLng(37.554752, 126.970631)
    val arrLoc=ArrayList<LatLng>()
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

    private fun initMap(){
        val mapFragment= childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(p0: GoogleMap) {
        googleMap=p0
        googleMap.mapType=GoogleMap.MAP_TYPE_NORMAL
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
        googleMap.setMinZoomPreference(10.0f)
        googleMap.setMaxZoomPreference(20.0f)
        val option=MarkerOptions()
        option.position(loc)
        option.icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )
        option.title("핀")
        option.snippet("지역 이름")
        googleMap.addMarker(option)?.showInfoWindow()
        //클릭하면 위도 경도 가져와서 이벤트 처리
        googleMap.setOnMapClickListener{
            arrLoc.add(it)
            val option2=MarkerOptions()
            option2.position(it)
            option2.icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            googleMap.addMarker(option2)
            val option3=PolylineOptions().color(Color.BLUE).addAll(arrLoc)
            googleMap.addPolyline(option3)
        }

    }
}