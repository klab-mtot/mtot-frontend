package com.example.mtot

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mtot.databinding.ActivityStartBinding
import com.example.mtot.retrofit2.LoginData
import com.example.mtot.retrofit2.LoginObject
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

        val loginInterface = LoginObject.retrofitInterface



        binding.googleButton.setOnClickListener {
            loginInterface.requestUrl().enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val str = response.toString()
                    Log.d("hello", str)
                    val i = str.indexOf("url")
                    val url = str.substring(i+4, str.length-1)
                    Log.d("hello", url)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }
            })
        }


//        loginInterface.requestTestMember().enqueue(object : Callback<TestMemberList>{
//            override fun onResponse(
//                call: Call<TestMemberList>,
//                response: Response<TestMemberList>
//            ) {
//                Log.d("hello", response.body()!!.toString())
//            }
//
//            override fun onFailure(call: Call<TestMemberList>, t: Throwable) {
//                Log.d("hello", t.message.toString())
//            }
//
//        })


//        binding.googleButton.setOnClickListener {
//            val i = Intent(this, MainActivity::class.java)
//            startActivity(i)
//        }
    }
}
