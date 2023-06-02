package com.example.mtot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtot.databinding.ActivityStartBinding
import com.example.mtot.retrofit2.LoginData

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    var logindata: List<LoginData>? = null
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


        val i = Intent(this, MainActivity::class.java)
        startActivity(i)

    }
}
