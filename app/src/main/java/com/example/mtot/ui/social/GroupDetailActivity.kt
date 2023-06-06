package com.example.mtot.ui.social

import android.app.ActivityGroup
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.R
import com.example.mtot.databinding.ActivityGroupDetailBinding
import com.example.mtot.retrofit2.GetTeamsResponse
import com.example.mtot.retrofit2.RequestAddMemberToTeam
import com.example.mtot.retrofit2.ResponseAddMemberToTeam
import com.example.mtot.retrofit2.TeamMembersResponse
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupDetailBinding
    lateinit var rvAdapter : SelectedMemberAdapter
    var recyclerDataList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        val retrofitInterface = getRetrofitInterface()
        val teamId = intent.getIntExtra("teamId", -1)
        retrofitInterface.requestTeamMember(teamId).enqueue(object: Callback<TeamMembersResponse>{
            override fun onResponse(
                call: Call<TeamMembersResponse>,
                response: Response<TeamMembersResponse>
            ) {
                Log.d("retrofit", response.body().toString())
                if(response.isSuccessful){
                    recyclerDataList.clear()
                    response.body()!!.memberList.forEach {
                        recyclerDataList.add(it.email)
                    }
                    rvAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<TeamMembersResponse>, t: Throwable) {
                Log.d("retrofit", t.message.toString())
            }

        })

        rvAdapter = SelectedMemberAdapter(recyclerDataList)
        binding.rvSelectedMembers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSelectedMembers.adapter = rvAdapter

        binding.button.setOnClickListener {
            val retrofitInterface = getRetrofitInterface()
            val str = binding.editTextMemberSearch.text.toString()
            val teamId = intent.getIntExtra("teamId", -1)
            retrofitInterface.addMemberToTeam(RequestAddMemberToTeam(teamId,str)).enqueue(object: Callback<ResponseAddMemberToTeam>{
                override fun onResponse(
                    call: Call<ResponseAddMemberToTeam>,
                    response: Response<ResponseAddMemberToTeam>
                ) {
                    if(response.isSuccessful){
                        rvAdapter.addItem(str)
                        Toast.makeText(this@GroupDetailActivity, "멤버 추가 완료", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@GroupDetailActivity, "멤버 추가 실패", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("hi", response.body().toString())
                }

                override fun onFailure(call: Call<ResponseAddMemberToTeam>, t: Throwable) {
                    Log.d("hi", t.message.toString())
                }

            })

            retrofitInterface.requestTeamMember(teamId).enqueue(object: Callback<TeamMembersResponse>{
                override fun onResponse(
                    call: Call<TeamMembersResponse>,
                    response: Response<TeamMembersResponse>
                ) {
                    Log.d("retrofit", response.body().toString())
                    if(response.isSuccessful){
                        recyclerDataList.clear()
                        response.body()!!.memberList.forEach {
                            recyclerDataList.add(it.email)
                        }
                        rvAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<TeamMembersResponse>, t: Throwable) {
                    Log.d("retrofit", t.message.toString())
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