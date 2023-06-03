package com.example.mtot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mtot.databinding.ActivityStartBinding
import com.example.mtot.retrofit2.LoginData
import com.example.mtot.retrofit2.LoginObject
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            val account = task.result!!
            val code = account.serverAuthCode.toString()
            Log.i("CODE", account.serverAuthCode.toString())

            loginInterface.requestUrl(code).enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        val str = response.body().toString()
                        Log.d("helloraw", str)
                    }
                    Log.d("hello", response.toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }
            })

        }

        binding.googleButton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
            )
                .requestEmail()
                .requestProfile()
                .requestServerAuthCode(getString(R.string.google_client_id))
                .build()
            val client = GoogleSignIn.getClient(this, gso)
            val i = Intent(client.signInIntent)
            launcher.launch(i)
//            loginInterface.requestUrl().enqueue(object : Callback<String> {
//
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    if(response.isSuccessful){
//                        val str = response.body().toString()
//                        Log.d("helloraw", str)
//                    }
////                    val str = response.body
////                    val i = str.indexOf("url")
////                    val url = str.substring(43+3, str.length-1)
////                    val index = Intent(Intent.ACTION_VIEW, Uri.parse(url))
////                    startActivity(index)
//                    Log.d("hello", response.toString())
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Log.d("hello", t.message.toString())
//                }
//            })
//
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
