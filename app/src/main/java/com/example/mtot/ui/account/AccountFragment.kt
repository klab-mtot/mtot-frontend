package com.example.mtot.ui.account

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mtot.MainActivity
import com.example.mtot.databinding.FragmentAccountBinding
import com.example.mtot.retrofit2.GetTeamResponse
import com.example.mtot.retrofit2.GetTeamsResponse
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.JourneysData
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getMyEmail
import com.example.mtot.retrofit2.getRetrofitInterface
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding
    lateinit var journeysData: JourneysData
    lateinit var groupData: GetTeamsResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        binding.imageView3.setOnClickListener {
            val i = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(i)
        }

        binding.username.text = getMyEmail(requireContext()).split("@")[0]

        binding.groupCount.setOnClickListener {
            (requireActivity() as MainActivity).selectSocialFragment()
        }

        binding.journeyConunt.setOnClickListener {
            (requireActivity() as MainActivity).selectMapFragmentAndHamburger()
        }


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val journeyInterface = getRetrofitInterface()
        journeyInterface.requestJourneysData().enqueue(object:Callback<JourneysData> {
            override fun onFailure(call: Call<JourneysData>, t: Throwable) {
                Log.d("hello",t.message.toString())
            }
            override fun onResponse(
                call: Call<JourneysData>,
                response: Response<JourneysData>
            ) {
                Log.d("hello",response.body().toString())
                if(response.isSuccessful){
                    journeysData = response.body()!!
                    binding!!.journeyConunt.text = journeysData!!.journeys.size.toString()
                }
            }
        })


        var groupInterface = getRetrofitInterface()
        groupInterface.getTeams().enqueue(object : Callback<GetTeamsResponse> {
            override fun onFailure(call: Call<GetTeamsResponse>, t: Throwable) {
                Log.d("Hello", t.message.toString())
            }

            override fun onResponse(
                call: Call<GetTeamsResponse>,
                response: Response<GetTeamsResponse>
            ) {
                Log.d("Hello", response.body().toString())
                if(response.isSuccessful) {
                    groupData = response.body()!!
                    binding!!.groupCount.text = groupData!!.count.toString()
                }
            }
        })
    }

}

