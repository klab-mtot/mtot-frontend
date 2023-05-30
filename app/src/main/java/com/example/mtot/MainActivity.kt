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
import com.example.mtot.ui.post.PostFragment
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
            if(binding.bnv.selectedItemId == R.id.navigation_post){
                val fragment = supportFragmentManager.findFragmentById(R.id.main_frm) as PostFragment
                if(fragment!=null)
                    fragment.addMark()
            }else {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, PostFragment()).commit()
                binding.bnv.selectedItemId = R.id.navigation_post
                binding.fab.setImageResource(R.drawable.ic_bottom_navigation_add)
                binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.secondary))
            }

        }
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, MapFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("itemid", item.itemId.toString())
        binding.fab.setImageResource(R.drawable.ic_bottom_navigation_plane)
        binding.fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.white))
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
}