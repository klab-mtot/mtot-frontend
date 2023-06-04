package com.example.mtot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mtot.databinding.ActivityStartBinding
import com.example.mtot.retrofit2.getLoginInterface
import com.example.mtot.retrofit2.saveAccessToken
import com.example.mtot.retrofit2.saveMyEmail
import com.example.mtot.retrofit2.savePostState
import com.example.mtot.retrofit2.setAccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init() {

        val loginInterface = getLoginInterface()

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            val account = task.result!!
            val code = account.serverAuthCode.toString()


            loginInterface.requestUrl(code).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val str = response.body().toString()
                        val responseBody = JSONObject(str)
                        saveAccessToken(this@StartActivity, responseBody.getString("accessToken"))
                        setAccessToken(responseBody.getString("accessToken"))
                        savePostState(this@StartActivity, false)
                        saveMyEmail(this@StartActivity, responseBody.getString("email"))
                        val i = Intent(this@StartActivity, MainActivity::class.java)
                        startActivity(i)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }
            })

        }

        binding.googleButton.setOnClickListener {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                    .requestProfile()
                    .requestServerAuthCode(getString(R.string.google_client_id))
                    .build()
            val client = GoogleSignIn.getClient(this, gso)
            val i = Intent(client.signInIntent)
            launcher.launch(i)
        }

    }
}
