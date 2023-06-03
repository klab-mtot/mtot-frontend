package com.example.mtot.ui.map

import android.graphics.Color
import android.os.Bundle
import android.service.autofill.Validators.or
import android.util.Log
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
import com.example.mtot.retrofit2.FriendData
import com.example.mtot.retrofit2.FriendObject
import com.example.mtot.retrofit2.FriendObject.friendInterface
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.JourneyObject
import com.example.mtot.retrofit2.SharedPreference
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentMapBinding
    lateinit var googleMap: GoogleMap

    var arrLoc = ArrayList<LatLng>()
    lateinit var journeyData: ArrayList<JourneyData>

    val dummyLatLngList = arrayListOf(
        LatLng(37.7749, -122.4194), // 예시 LatLng 데이터
        LatLng(37.7748, -122.4195),
        LatLng(37.7750, -122.4193)
    )


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
        val journeyInterface = JourneyObject.journeyInterface
        Log.d("Hello", "hi1")
        /*
        journeyInterface.requestJourneyData().enqueue(object : Callback<ArrayList<JourneyData>> {
            override fun onFailure(call: Call<ArrayList<JourneyData>>, t: Throwable) {
                Log.d("Hello", "실패")
            }
            override fun onResponse(
                call: Call<ArrayList<JourneyData>>,
                response: Response<ArrayList<JourneyData>>
            ) {
                journeyData = response.body()!!
                arrLoc = journeyData.filterIsInstance<LatLng>() as ArrayList<LatLng>
*/

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@MapFragment)
        Log.d("Hello", "성공")


    }


    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dummyLatLngList[0], 16.0f))
        googleMap.setMinZoomPreference(10.0f)
        googleMap.setMaxZoomPreference(20.0f)

        Log.d("Hello", "hi")

        val option = MarkerOptions()
        option.position(dummyLatLngList[0])
        option.icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )

        for (loc in dummyLatLngList) {
            googleMap.addMarker(MarkerOptions().position(loc))
        }


        /*
                googleMap.setOnMapClickListener {
                    arrLoc.add(it)
                    val option2 = MarkerOptions()
                    option2.position(it)
                    option2.icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                    )
                    googleMap.addMarker(option2)
                    val option3 = PolylineOptions().color(Color.BLUE).addAll(arrLoc)
                    googleMap.addPolyline(option3)
                }
        */
    }
}