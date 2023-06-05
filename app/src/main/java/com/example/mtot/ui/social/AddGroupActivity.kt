package com.example.mtot.ui.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.databinding.ActivityAddGroupBinding
import com.example.mtot.retrofit2.AddTeamRequest
import com.example.mtot.retrofit2.AddTeamResponse
import com.example.mtot.retrofit2.GetFriendResponse
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddGroupActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){

        binding.button.setOnClickListener {
            val str = binding.editTextGroupName.text.toString()
            val retrofitInterface = getRetrofitInterface()
            retrofitInterface.addTeam(AddTeamRequest(str)).enqueue(object: Callback<AddTeamResponse>{
                override fun onResponse(
                    call: Call<AddTeamResponse>,
                    response: Response<AddTeamResponse>
                ) {
                    Log.d("hello", response.body().toString())
                    if(response.isSuccessful){
                        finish()
                    }
                }

                override fun onFailure(call: Call<AddTeamResponse>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }

            })
        }

        binding.ivAddGroupBack.setOnClickListener {
            finish()
        }
        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }
    }


}