package com.example.mtot

import android.content.Intent
import android.content.res.ColorStateList
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.mtot.databinding.ActivityMainBinding
import com.example.mtot.ui.account.AccountFragment
import com.example.mtot.ui.calendar.CalendarFragment
import com.example.mtot.ui.map.MapFragment
import com.example.mtot.ui.map.MapHamburgerFragment
import com.example.mtot.ui.post.AddJourney
import com.example.mtot.ui.post.PostFragment
import com.example.mtot.ui.post.PostHamburgerFragment
import com.example.mtot.ui.social.SocialFragment
import com.google.android.material.navigation.NavigationBarView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.mtot.retrofit2.getAccessToken
import com.example.mtot.retrofit2.getMyEmail
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener{

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("hello", getAccessToken(this))
        Log.d("hello", getMyEmail(this))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnv.setOnItemSelectedListener{
            onNavigationItemSelected(it)
        }


        //핀 10분마다 자동생성=====================================
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val photoWorkRequest = PeriodicWorkRequestBuilder<PhotoWorker>(
            repeatInterval = 10, // 10 minutes
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork("PhotoWorker", ExistingPeriodicWorkPolicy.KEEP, photoWorkRequest)


    //=============================================================

    binding.fab.setOnClickListener {
            if(binding.bnv.selectedItemId == R.id.navigation_post){
                val fragment = supportFragmentManager.findFragmentById(R.id.main_frm) as PostFragment
                fragment.addMark()
            }else {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, PostFragment()).commit()
                binding.bnv.selectedItemId = R.id.navigation_post
                binding.fab.setImageResource(R.drawable.ic_bottom_navigation_add)
                binding.fab.imageTintList = ColorStateList.valueOf(getColor(R.color.black))
                binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.secondary))
                val i = Intent(this, AddJourney::class.java)
                startActivity(i)
            }
        }
        binding.antiHamburgerFrm.setOnClickListener {
            binding.llHamburgerFrm.visibility = View.GONE
        }
        binding.hamburgerFrm.setOnClickListener {
            //do nothing just cover event
        }
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, MapFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("itemid", item.itemId.toString())
        binding.fab.setImageResource(R.drawable.ic_bottom_navigation_plane)
        binding.fab.supportBackgroundTintList = ColorStateList.valueOf(getColor(R.color.primary))
        binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.white))
        binding.fab.imageTintList = ColorStateList.valueOf(getColor(R.color.white))
        when(item.itemId){
            R.id.navigation_map -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, MapFragment()).commit()
                return true
            }
            R.id.navigation_calendar -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, CalendarFragment()).commit()
                return true
            }
            R.id.navigation_social -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, SocialFragment()).commit()
                return true
            }
            R.id.navigation_account -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, AccountFragment()).commit()
                return true
            }
            R.id.navigation_post -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, PostFragment()).commit()
                return true
            }
            else -> return true
        }
    }

    fun showPostHamburgerToolbar(){
        binding.llHamburgerFrm.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().replace(R.id.hamburger_frm, PostHamburgerFragment()).commit()
    }

    fun showMapHamburgerToolbar(){
        binding.llHamburgerFrm.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().replace(R.id.hamburger_frm, MapHamburgerFragment()).commit()
    }
}