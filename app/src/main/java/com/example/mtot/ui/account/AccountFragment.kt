package com.example.mtot.ui.account

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mtot.databinding.FragmentAccountBinding
import com.example.mtot.retrofit2.GetTeamResponse
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.getRetrofitInterface
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding
    lateinit var journeyData: List<JourneyData>
    lateinit var groupData: List<GetTeamResponse>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        val journeyInterface = getRetrofitInterface()
        journeyInterface.requestJourneyData().enqueue(object:Callback<List<JourneyData>> {
            override fun onFailure(call: Call<List<JourneyData>>, t: Throwable) {
                TODO("Not yet implemented")
            }
            override fun onResponse(
                call: Call<List<JourneyData>>,
                response: Response<List<JourneyData>>
            ) {
                journeyData = response.body()!!
            }
        })

        binding.journeyConunt.text = journeyData.size.toString()


        var groupInterface = getRetrofitInterface()
//        groupInterface.getTeams().enqueue(object : Callback<List<GetTeamResponse>> {
//            override fun onFailure(call: Call<List<GetTeamResponse>>, t: Throwable) {
//                Log.d("Hello", "실패")
//            }
//
//            override fun onResponse(
//                call: Call<List<GetTeamResponse>>,
//                response: Response<List<GetTeamResponse>>
//            ) {
//                groupData = response.body()!!
//            }
//        })

        binding.groupCount.text = groupData.size.toString()




        binding.imageView3.setOnClickListener {
            val i = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(i)
        }


        return binding.root
    }
}

