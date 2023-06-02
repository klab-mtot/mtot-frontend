package com.example.mtot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import com.example.mtot.databinding.ActivityLoginBinding
import com.example.mtot.ui.login.LoginFragment
import com.example.mtot.ui.login.SetProfileFragment
import com.example.mtot.ui.login.SignupFragment

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){

        val callback = onBackPressedDispatcher.addCallback {
            val i = Intent(this@LoginActivity, StartActivity::class.java)
            startActivity(i)
        }

        val mode = intent.getStringExtra("mode")
        if(mode != null) {
            when (mode) {
                "login" -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.login_frm, LoginFragment()).commit()
                }

                "signup" -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.login_frm, SignupFragment()).commit()
                }

                "profile" -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.login_frm, SetProfileFragment()).commit()
                }

                else -> {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }
            }
        } else {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}