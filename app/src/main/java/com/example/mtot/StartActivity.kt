package com.example.mtot

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mtot.databinding.ActivityStartBinding
import com.example.mtot.retrofit2.FriendObject
import com.example.mtot.retrofit2.LoginData
import com.example.mtot.retrofit2.LoginObject
import com.example.mtot.retrofit2.SharedPreference.saveAccessToken
import com.example.mtot.retrofit2.SharedPreference.saveMyEmail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    var logindata: LoginData? = null
    var accessToken: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init() {

//        val loginInterface=LoginObject.loginInterface
//
//        binding.googleButton.setOnClickListener {
//            loginInterface.requestLogin().enqueue(object:Callback<String> {

//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    val str = response.toString()
//                    val i = str.indexOf("url")
//                    val url = str.substring(43+3, str.length-1)
//                    val index = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    startActivity(index)
//                    Log.d("hello", url)
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Log.d("hello", t.message.toString())
//                }
//            })

        binding.googleButton.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }


    }
}
