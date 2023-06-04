package com.example.mtot.ui.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.mtot.JourneyDetailActivity
import com.example.mtot.MainActivity
import com.example.mtot.R
import com.example.mtot.databinding.FragmentMapBinding
import com.example.mtot.retrofit2.JourneysData
import com.example.mtot.retrofit2.getRetrofitInterface
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


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
        binding.cvMapHamburgerButton.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.showMapHamburgerToolbar()
        }
    }

    private fun initMap() {
        val retrofitInterface = getRetrofitInterface()

        retrofitInterface.requestJourneysData().enqueue(object : Callback<JourneysData> {
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
                        if (it.pins.size > 0) {
                            arrLoc.add(
                                LatLng(
                                    it.pins[0].location.latitude,
                                    it.pins[0].location.longitude
                                )
                            )
                        }
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
        if (!arrLoc.isEmpty()) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arrLoc[0], 9.0f))
            Log.d("PIN", "가져올 핀이 있음ㅇㅇ : 핀 개수(여정 개수) = ${arrLoc.size}")
        } else {
            Log.d("PIN", "가져올 핀이 없음ㅇㅇ")
            googleMap.addMarker(MarkerOptions().position(LatLng(36.5000, 127.5000)))
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(36.5000, 127.5000),
                    8.0f
                )
            )
        }
        googleMap.setMinZoomPreference(5.0f)
        googleMap.setMaxZoomPreference(20.0f)

        val option = MarkerOptions()
        option.icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )
        arrLoc.forEach {
            //핀 찍어서 보여주기
            option.position(it)
            googleMap.addMarker(option)
        }



        googleMap.setOnMarkerClickListener(this@MapFragment)


        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        googleMap.isMyLocationEnabled=true
    }

//        googleMap.setOnMapClickListener { loc ->
//            arrLoc.add(loc)
//            val option2 = MarkerOptions()
//            option2.position(loc)
//            option2.icon(
//                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
//            )
//            googleMap.addMarker(option2)
//            val option3 = PolylineOptions().color(Color.BLUE).addAll(arrLoc)
//            googleMap.addPolyline(option3)
//        }
//    }

    override fun onMarkerClick(marker: Marker): Boolean {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15.0f))
        val intent=Intent(requireContext(),JourneyDetailActivity::class.java)
        return true
    }

}
