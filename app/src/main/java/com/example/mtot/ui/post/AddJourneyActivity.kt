package com.example.mtot.ui.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.mtot.MainActivity
import com.example.mtot.databinding.ActivityAddJourneyBinding
import com.example.mtot.retrofit2.AddJourneyRequest
import com.example.mtot.retrofit2.AddJourneyResponse
import com.example.mtot.retrofit2.GetTeamResponse
import com.example.mtot.retrofit2.GetTeamsResponse
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddJourneyActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddJourneyBinding
    lateinit var dataList : ArrayList<String>
    lateinit var teamsList : GetTeamsResponse
    lateinit var adapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJourneyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){


//        dataList = arrayListOf(
//            "그룹1",
//            "그룹2",
//            "그룹3"
//        )



        val retrofitInterface = getRetrofitInterface()
        /* 최초에 팀 정보 로딩 */
        retrofitInterface.getTeams().enqueue(object: retrofit2.Callback<GetTeamsResponse>{
            override fun onResponse(
                call: Call<GetTeamsResponse>,
                response: Response<GetTeamsResponse>
            ) {
                Log.d("hello", response.toString())
                if(response.isSuccessful){
                    teamsList = response.body()!!
                    val nameList = teamsList.teamList.map{
                        it.teamName
                    }
                    dataList = ArrayList<String>()
                    dataList.addAll(nameList)

                    adapter = ArrayAdapter<String>(this@AddJourneyActivity, android.R.layout.simple_spinner_dropdown_item, dataList)
                    binding.spGroupSelect.adapter = adapter
                }
            }

            override fun onFailure(call: Call<GetTeamsResponse>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

        })

        binding.ivAddJourneyBack.setOnClickListener {
            val intent = Intent(this@AddJourneyActivity, MainActivity::class.java)
            setResult(-1, intent)
            Log.d("hello", "set result 0")
            finish()
        }


        /* add journey 버튼 클릭 */
        binding.buttonAddJourney.setOnClickListener {
            val requestBody = AddJourneyRequest(
                binding.editTextJourneyName.text.toString(),
                teamsList.teamList[binding.spGroupSelect.selectedItemPosition].teamId
            )
            retrofitInterface.addJourney(requestBody).enqueue(object: Callback<AddJourneyResponse> {
                override fun onResponse(
                    call: Call<AddJourneyResponse>,
                    response: Response<AddJourneyResponse>
                ) {
                    Log.d("hello", response.toString())
                    val journeyId = response.body()!!.journeyId
                    val intent = Intent(this@AddJourneyActivity, MainActivity::class.java)
                    setResult(journeyId, intent)
                    finish()
                }

                override fun onFailure(call: Call<AddJourneyResponse>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }

            })
        }
    }

}