package com.example.mtot

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.mtot.databinding.ActivityMainBinding
import com.example.mtot.ui.account.AccountFragment
import com.example.mtot.ui.calendar.CalendarFragment
import com.example.mtot.ui.map.MapFragment
import com.example.mtot.ui.nothing.NothingFragment
import com.example.mtot.ui.social.SocialFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnv.setOnItemSelectedListener{
            onNavigationItemSelected(it)
        }
        binding.fab.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.main_frm, NothingFragment()).commit()
            binding.bnv.selectedItemId = R.id.navigation_nothing
            binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.primary))
        }
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, MapFragment()).commit()
        binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.black))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("itemid", item.itemId.toString())
        binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.black))
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
            else -> return true
        }
    }
}