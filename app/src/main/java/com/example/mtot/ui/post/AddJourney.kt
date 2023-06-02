package com.example.mtot.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.mtot.databinding.ActivityAddJourneyBinding
import com.example.mtot.retrofit2.GetTeamResponse

class AddJourney : AppCompatActivity() {
    lateinit var binding : ActivityAddJourneyBinding
    lateinit var dataList : ArrayList<String>
    lateinit var teamList : ArrayList<GetTeamResponse>
    lateinit var adapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJourneyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){


        dataList = arrayListOf(
            "그룹1",
            "그룹2",
            "그룹3"
        )

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dataList)
        binding.spGroupSelect.adapter = adapter


        /* 최초에 팀 정보 로딩 */
//        groupInterface.getTeams().enqueue(object: retrofit2.Callback<List<GetTeamResponse>>{
//            override fun onResponse(
//                call: Call<List<GetTeamResponse>>,
//                response: Response<List<GetTeamResponse>>
//            ) {
//                Log.d("hello", response.toString())
//                if(response.isSuccessful){
//                    teamList = ArrayList<GetTeamResponse>()
//                    teamList.addAll(response.body()!!)
//                    val nameList = response.body()!!.map {
//                        it.teamName
//                    }.toList()
//                    dataList = ArrayList<String>()
//                    dataList.addAll(nameList)
//                }
//            }
//
//            override fun onFailure(call: Call<List<GetTeamResponse>>, t: Throwable) {
//                Log.d("hello", t.message.toString())
//            }
//
//        })

        binding.ivAddJourneyBack.setOnClickListener {
            finish()
        }

        binding.button.setOnClickListener {
            finish()
        }

        /* add journey 버튼 클릭 */
//        binding.button.setOnClickListener {
//            val requestBody = AddJourneyRequest(
//                binding.editTextJourneyName.text.toString(),
//                teamList[binding.spGroupSelect.selectedItemPosition].teamId
//            )
//            groupInterface.addJourney(requestBody).enqueue(object: Callback<AddJourneyResponse>{
//                override fun onResponse(
//                    call: Call<AddJourneyResponse>,
//                    response: Response<AddJourneyResponse>
//                ) {
//                    Log.d("hello", response.toString())
//                    finish()
//                }
//
//                override fun onFailure(call: Call<AddJourneyResponse>, t: Throwable) {
//                    Log.d("hello", t.message.toString())
//                }
//
//            })
//        }
    }

}