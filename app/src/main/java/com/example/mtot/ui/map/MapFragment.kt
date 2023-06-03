package com.example.mtot.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mtot.MainActivity
import com.example.mtot.R
import com.example.mtot.databinding.FragmentMapBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.getRetrofitInterface
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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
        val journeyInterface = getRetrofitInterface()
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
    }


    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dummyLatLngList[0], 16.0f))
        googleMap.setMinZoomPreference(10.0f)
        googleMap.setMaxZoomPreference(20.0f)

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