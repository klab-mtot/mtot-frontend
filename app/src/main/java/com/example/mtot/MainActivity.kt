package com.example.mtot

import android.content.Intent
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener{

    private lateinit var binding: ActivityMainBinding
    var journeyId : Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnv.setOnItemSelectedListener{
            onNavigationItemSelected(it)
        }


        //핀 10분마다 자동생성=====================================
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.UNMETERED)
//            .build()
//
//        val photoWorkRequest = PeriodicWorkRequestBuilder<PhotoWorker>(
//            repeatInterval = 10, // 10 minutes
//            repeatIntervalTimeUnit = TimeUnit.MINUTES
//        )
//            .setConstraints(constraints)
//            .build()
//
//        WorkManager.getInstance(applicationContext)
//            .enqueueUniquePeriodicWork("PhotoWorker", ExistingPeriodicWorkPolicy.KEEP, photoWorkRequest)


    //=============================================================
    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == -1){
            Log.d("hello","add journey error")
            binding.bnv.selectedItemId = R.id.navigation_map
        } else {
            journeyId = result.resultCode
            Log.d("hello", "journeyId : " + journeyId)
        }
    }

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

                val i = Intent(this@MainActivity, AddJourneyActivity::class.java)
                resultLauncher.launch(i)
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