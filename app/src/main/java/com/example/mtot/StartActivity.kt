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
    var logindata:LoginData?=null
    var accessToken:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){

        val loginInterface=LoginObject.loginInterface

        binding.googleButton.setOnClickListener {
            loginInterface.requestLogin().enqueue(object:Callback<LoginData> {
                override fun onFailure(call: Call<LoginData>, t: Throwable) {
                    t.message?.let { it1 -> Log.e("LOGIN", it1) }
                    var dialog = AlertDialog.Builder(this@StartActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                    logindata = response.body()
                    saveAccessToken(this@StartActivity, logindata?.accessToken.toString())
                    saveMyEmail(this@StartActivity, logindata?.email.toString())

//                  accessToken=logindata?.accessToken
                    FriendObject.accessToken = accessToken
                    Log.d("LOGIN","msg : "+logindata?.accessToken)
                    Log.d("LOGIN","code : "+logindata?.email)
                    var dialog = AlertDialog.Builder(this@StartActivity)
                    dialog.setTitle(logindata?.accessToken)
                    dialog.setMessage(logindata?.email)
                    dialog.show()
                }
            })


/*
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
*/
        }
    }
}