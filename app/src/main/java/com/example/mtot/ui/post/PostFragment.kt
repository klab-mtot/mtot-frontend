package com.example.mtot.ui.post

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.mtot.MainActivity
import com.example.mtot.R
import com.example.mtot.databinding.FragmentPostBinding
import com.example.mtot.retrofit2.LocationData
import com.example.mtot.retrofit2.RequestAddPin
import com.example.mtot.retrofit2.ResponseAddPhotoToPin
import com.example.mtot.retrofit2.ResponseAddPin
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getRetrofitExceptJsonInterface
import com.example.mtot.retrofit2.getRetrofitInterface
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentPostBinding
    lateinit var googleMap: GoogleMap
    var myCurrentLoc = LatLng(36.5, 127.5)
    val previousLoc = ArrayList<LatLng>()
    var lastestPinNum = -1

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fcv_post_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.cvPostHamburgerButton.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.showPostHamburgerToolbar()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.setMinZoomPreference(10.0f)
        googleMap.setMaxZoomPreference(20.0f)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myCurrentLoc))
        val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        val location0 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location0 != null && location1 != null) {
            myCurrentLoc = LatLng(
                (location0.latitude + location1.latitude) / 2,
                (location0.longitude + location1.longitude) / 2
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLoc, 15.0f))
            addPhotoWorker()
            drawMarkers()
        } else {
            myCurrentLoc = LatLng(36.5,127.5)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLoc, 15.0f))
        }
    }


    @SuppressLint("MissingPermission")
    fun setLocationUpdate() {
        val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,
            1.0f,
            myLocationListener
        )
    }

    fun removeLocationUpdate(){
        val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(myLocationListener)
    }

    val myLocationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location) {
            myCurrentLoc = LatLng(p0.latitude, p0.longitude)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLoc, 15.0f))
            addPhotoWorker()
            drawMarkers()
        }
    }



    @SuppressLint("Range")
    fun addPhotoWorker() {
        //핀 10분마다 자동생성=====================================
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.UNMETERED)  //wifi connected
//            .build()
//
//        val photoWorkRequest = OneTimeWorkRequestBuilder<PhotoWorker>()
//            .setConstraints(constraints)
//            .build()
//
//        WorkManager.getInstance(applicationContext)
//            .enqueueUniqueWork("PhotoWorker", ExistingWorkPolicy.KEEP, photoWorkRequest)
        //=============================================================

        val ALBUM_DIRECTORY =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()
        Log.d("qwerty1", ALBUM_DIRECTORY)
        val currentTime = System.currentTimeMillis()
        val tenMinutesAgo = currentTime - 10 * 60 * 1000
        val selection = "${MediaStore.Images.ImageColumns.DATE_TAKEN} >= $tenMinutesAgo"
        val sortOrder = "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
        val cursor = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )

        val retrofitInterface = getRetrofitInterface()

        val files = ArrayList<MultipartBody.Part>()
        cursor?.use {
            while (it.moveToNext()) {
                val imagePath = it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
                Log.d("hello", "imagePath : " + imagePath)
                val imageFile = File(imagePath)
                files.add(
                    MultipartBody.Part.createFormData(
                        "photos", imageFile.name,
                        imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    )
                )
            }
        }
        var pinId = -1
        val retrofitInterfaceExceptJson = getRetrofitExceptJsonInterface()
        retrofitInterface.addPinToJourney(
            RequestAddPin(
                getJourneyId(requireContext()),
                LocationData(myCurrentLoc.latitude, myCurrentLoc.longitude)
            )
        ).enqueue(object : Callback<ResponseAddPin> {
            override fun onResponse(
                call: Call<ResponseAddPin>,
                response: Response<ResponseAddPin>
            ) {
                Log.d("hello", response.body().toString())
                if (response.isSuccessful) {
                    pinId = response.body()!!.pinId
                    if (lastestPinNum != pinId) {
                        lastestPinNum = pinId
                        addMark()
                    }
                    val pinIdRequestBody =
                        pinId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val requestList = files.toList()
                    retrofitInterfaceExceptJson.addPhotoToPin(pinIdRequestBody, requestList)
                        .enqueue(object : Callback<ResponseAddPhotoToPin> {
                            override fun onResponse(
                                call: Call<ResponseAddPhotoToPin>,
                                response: Response<ResponseAddPhotoToPin>
                            ) {
                                Log.d("hello", response.body().toString())
                            }

                            override fun onFailure(
                                call: Call<ResponseAddPhotoToPin>,
                                t: Throwable
                            ) {
                                Log.d("hello", t.message.toString())
                            }

                        })
                }
            }

            override fun onFailure(call: Call<ResponseAddPin>, t: Throwable) {
                t.message.toString()
            }
        })
    }


    fun drawMarkers() {
        googleMap.clear()
        previousLoc.forEach {
            val markerOption = MarkerOptions()
            markerOption.position(it)
            markerOption.icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            googleMap.addMarker(markerOption)
        }
        val polylineOption = PolylineOptions().color(Color.BLUE).addAll(previousLoc)
        googleMap.addPolyline(polylineOption)

        val curMarkerOption = MarkerOptions()
        curMarkerOption.position(myCurrentLoc)
        curMarkerOption.icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        )
        googleMap.addMarker(curMarkerOption)
    }

    fun clearPin(){
        previousLoc.clear()
    }

    fun addMark() {
        previousLoc.add(myCurrentLoc)
    }
}