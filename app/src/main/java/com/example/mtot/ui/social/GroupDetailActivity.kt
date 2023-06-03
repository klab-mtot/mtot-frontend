package com.example.mtot.ui.social

import android.app.ActivityGroup
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.R
import com.example.mtot.databinding.ActivityGroupDetailBinding
import com.example.mtot.retrofit2.RequestAddMemberToTeam
import com.example.mtot.retrofit2.ResponseAddMemberToTeam
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupDetailBinding
    lateinit var rvAdapter : SelectedMemberAdapter
    lateinit var recyclerDataList : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        recyclerDataList = ArrayList<String>()

        rvAdapter = SelectedMemberAdapter(recyclerDataList)
        binding.rvSelectedMembers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSelectedMembers.adapter = rvAdapter

        binding.button.setOnClickListener {
            val retrofitInterface = getRetrofitInterface()
            val str = binding.editTextMemberSearch.text.toString()
            val teamId = intent.getIntExtra("teamId", -1)
            Log.d("hello",RequestAddMemberToTeam(teamId,str).toString())
            retrofitInterface.addMemberToTeam(RequestAddMemberToTeam(teamId,str)).enqueue(object: Callback<ResponseAddMemberToTeam>{
                override fun onResponse(
                    call: Call<ResponseAddMemberToTeam>,
                    response: Response<ResponseAddMemberToTeam>
                ) {
                    if(response.isSuccessful){
                        rvAdapter.addItem(str)
                    }
                    Log.d("hello", response.body().toString())
                }

                override fun onFailure(call: Call<ResponseAddMemberToTeam>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }

            })

        }

        binding.ivAddGroupDetailBack.setOnClickListener {
            finish()
        }


        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }

    }
}