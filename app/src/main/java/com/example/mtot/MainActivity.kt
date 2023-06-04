package com.example.mtot

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.view.MenuItem
import android.view.View
import com.example.mtot.databinding.ActivityMainBinding
import com.example.mtot.ui.account.AccountFragment
import com.example.mtot.ui.calendar.CalendarFragment
import com.example.mtot.ui.map.MapFragment
import com.example.mtot.ui.map.MapHamburgerFragment
import com.example.mtot.ui.post.AddJourneyActivity
import com.example.mtot.ui.post.PostFragment
import com.example.mtot.ui.post.PostHamburgerFragment
import com.example.mtot.ui.social.SocialFragment
import com.google.android.material.navigation.NavigationBarView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.example.mtot.retrofit2.LocationData
import com.example.mtot.retrofit2.RequestAddPin
import com.example.mtot.retrofit2.ResponseAddPhotoToPin
import com.example.mtot.retrofit2.ResponseAddPin
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getPostState
import com.example.mtot.retrofit2.getRetrofitExceptJsonInterface
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.retrofit2.saveJourneyId
import com.example.mtot.retrofit2.savePostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    val mapFragment = MapFragment()
    val calendarFragment = CalendarFragment()
    val postFragment = PostFragment()
    val socialFragment = SocialFragment()
    val accountFragment = AccountFragment()


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getStoragePermission()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnv.setOnItemSelectedListener {
            onNavigationItemSelected(it)
        }

        supportFragmentManager.beginTransaction().add(R.id.main_frm, mapFragment).hide(mapFragment)
            .add(R.id.main_frm, calendarFragment).hide(calendarFragment)
            .add(R.id.main_frm, postFragment).hide(postFragment)
            .add(R.id.main_frm, socialFragment).hide(socialFragment)
            .add(R.id.main_frm, accountFragment).hide(accountFragment).show(mapFragment)
            .commit()

        binding.antiHamburgerFrm.setOnClickListener {
            binding.llHamburgerFrm.visibility = View.GONE
        }
        binding.hamburgerFrm.setOnClickListener {
            //do nothing just cover event
        }

        binding.fab.setOnClickListener {
            if (getPostState(this)) {
                if (binding.bnv.selectedItemId != R.id.navigation_post) {
                    showPostFragment()
                }
            } else {
                showPostFragment()
                binding.bnv.selectedItemId = R.id.navigation_post
                binding.fab.setImageResource(R.drawable.ic_bottom_navigation_add)
                binding.fab.imageTintList = ColorStateList.valueOf(getColor(R.color.black))
                binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.secondary))

                val i = Intent(this@MainActivity, AddJourneyActivity::class.java)
                resultLauncher.launch(i)
            }
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
        val currentTime = System.currentTimeMillis()
        val tenMinutesAgo = currentTime - 10 * 60 * 1000
        val selection = "${MediaStore.Images.ImageColumns.DATE_TAKEN} >= $tenMinutesAgo"
        val sortOrder = "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
        val cursor = applicationContext.contentResolver.query(
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
                getJourneyId(applicationContext),
                LocationData(36.5, 127.5)
            )
        ).enqueue(object : Callback<ResponseAddPin> {
            override fun onResponse(
                call: Call<ResponseAddPin>,
                response: Response<ResponseAddPin>
            ) {
                Log.d("hello", response.body().toString())
                if (response.isSuccessful) {
                    pinId = response.body()!!.pinId
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (!getPostState(this)) {
            binding.fab.setImageResource(R.drawable.ic_bottom_navigation_plane)
            binding.fab.supportBackgroundTintList =
                ColorStateList.valueOf(getColor(R.color.primary))
            binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.white))
            binding.fab.imageTintList = ColorStateList.valueOf(getColor(R.color.white))
        }
        when (item.itemId) {
            R.id.navigation_map -> {
                showMapFragment()
                return true
            }

            R.id.navigation_calendar -> {
                showCalendarFragment()
                return true
            }

            R.id.navigation_social -> {
                showSocialFragment()
                return true
            }

            R.id.navigation_account -> {
                showAccountfragment()
                return true
            }

            R.id.navigation_post -> {
                showPostFragment()
                if (!getPostState(this))
                    savePostState(this, true)
                return true
            }

            else -> return true
        }
    }

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("hello", result.resultCode.toString())
            if (result.resultCode == -1) {
                binding.bnv.selectedItemId = R.id.navigation_map
            } else {    //만약 journey를 만들었으면
                saveJourneyId(this@MainActivity, result.resultCode)
                //addPhotoWorker()
                postFragment.setLocationUpdate()
            }
        }

    fun showPostHamburgerToolbar() {
        binding.llHamburgerFrm.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.hamburger_frm, PostHamburgerFragment()).commit()
    }

    fun showMapHamburgerToolbar() {
        binding.llHamburgerFrm.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.hamburger_frm, MapHamburgerFragment()).commit()
    }

    fun showMapFragment() {
        supportFragmentManager.beginTransaction().hide(calendarFragment)
            .hide(postFragment).hide(socialFragment).hide(accountFragment).show(mapFragment)
            .commit()
    }

    fun showCalendarFragment() {
        supportFragmentManager.beginTransaction().hide(mapFragment)
            .hide(postFragment).hide(socialFragment).hide(accountFragment)
            .show(calendarFragment)
            .commit()
    }

    fun showPostFragment() {
        supportFragmentManager.beginTransaction().hide(mapFragment).hide(calendarFragment)
            .hide(socialFragment).hide(accountFragment).show(postFragment)
            .commit()
    }

    fun showSocialFragment() {
        supportFragmentManager.beginTransaction().hide(mapFragment).hide(calendarFragment)
            .hide(postFragment).hide(accountFragment).show(socialFragment)
            .commit()
    }

    fun showAccountfragment() {
        supportFragmentManager.beginTransaction().hide(mapFragment).hide(calendarFragment)
            .hide(postFragment).hide(socialFragment).show(accountFragment)
            .commit()
    }


    val requestFineGPSPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Toast.makeText(this, "Fine GPS 권한 허용됨", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fine GPS 권한 거부됨", Toast.LENGTH_SHORT).show()
            }
        }

    fun getFineGPSPermission() {
        when {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
                    PackageManager.PERMISSION_GRANTED -> {
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
            }

            else -> {
                requestFineGPSPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }


    val requestCoarseGPSPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            getFineGPSPermission()
            if (it) {
                Toast.makeText(this, "Coarse GPS 권한 허용됨", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Coarse GPS 권한 거부됨", Toast.LENGTH_SHORT).show()
            }
        }

    fun getCoarseGPSPermission() {
        when {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) ==
                    PackageManager.PERMISSION_GRANTED -> {
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
            }

            else -> {
                requestCoarseGPSPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }



    val requestStoragePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            getCoarseGPSPermission()
            if (it) {
                Toast.makeText(this, "저장소 접근 권한 허용됨", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "저장소 접근 권한 거부됨", Toast.LENGTH_SHORT).show()
            }
        }

    fun getStoragePermission() {
        when {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) ==
                    PackageManager.PERMISSION_GRANTED -> {
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
            }

            else -> {
                requestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

}