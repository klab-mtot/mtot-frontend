package com.example.mtot

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
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
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.work.*
import com.example.mtot.retrofit2.getPostState
import com.example.mtot.retrofit2.saveJourneyId
import com.example.mtot.retrofit2.savePostState

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
                    binding.bnv.selectedItemId = R.id.navigation_post
                    showPostFragment()
                }
            } else {
                showPostFragment()
                binding.bnv.selectedItemId = R.id.navigation_post
                binding.fab.setImageResource(R.drawable.ic_bottom_navigation_trail)
                binding.fab.imageTintList = ColorStateList.valueOf(getColor(R.color.black))
                binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.secondary))

                val i = Intent(this@MainActivity, AddJourneyActivity::class.java)
                resultLauncher.launch(i)
            }
        }
    }

    fun selectSocialFragment(){
        binding.bnv.selectedItemId = R.id.navigation_social
    }

    fun selectMapFragmentAndHamburger(){
        binding.bnv.selectedItemId = R.id.navigation_map
        showMapHamburgerToolbar()
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