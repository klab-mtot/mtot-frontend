package com.example.mtot.ui.account

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mtot.databinding.FragmentAccountBinding
import com.example.mtot.retrofit2.GetTeamResponse
import com.example.mtot.retrofit2.JourneyData
import com.google.android.gms.maps.model.LatLng

class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding
    lateinit var journeyData: ArrayList<JourneyData>
    lateinit var groupData: List<GetTeamResponse>

    var dummyJourneyData = arrayListOf(
        JourneyData(
            1,
            "dfdsa",
            LatLng(11.0, 11.0),
            Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        ),
        JourneyData(
            1,
            "dfdsa",
            LatLng(11.0, 11.0),
            Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        ),
        JourneyData(
            1,
            "dfdsa",
            LatLng(11.0, 11.0),
            Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        ),
        JourneyData(
            1,
            "dfdsa",
            LatLng(11.0, 11.0),
            Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        )
    )


    var dummyGroupData = arrayListOf(
        GetTeamResponse(
            1,
            "dfdsa"
        ), GetTeamResponse(
            1,
            "dfdsa"
        ), GetTeamResponse(
            1,
            "dfdsa"
        ), GetTeamResponse(
            1,
            "dfdsa"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        //개수 가져와
        //읽어서 사이즈 세.


        /* 아직 데이터가 없어서..
                val journeyInterface = JourneyObject.journeyInterface
                journeyInterface.requestJourneyData().enqueue(object : Callback<ArrayList<JourneyData>> {
                    override fun onFailure(call: Call<ArrayList<JourneyData>>, t: Throwable) {
                        Log.d("Hello", "실패")
                    }

                    override fun onResponse(
                        call: Call<ArrayList<JourneyData>>,
                        response: Response<ArrayList<JourneyData>>
                    ) {
                        journeyData = response.body()!!
                    }
                })
                binding.journeyConunt.text=journeyData.size.toString()

        */
        binding.journeyConunt.text = dummyJourneyData.size.toString() //더미 데이터

        /* 아직 데이터가 없어서..
                var groupInterface = GroupObject.groupInterface
                groupInterface.getTeams().enqueue(object : Callback<List<GetTeamResponse>> {
                    override fun onFailure(call: Call<List<GetTeamResponse>>, t: Throwable) {
                        Log.d("Hello", "실패")
                    }

                    override fun onResponse(
                        call: Call<List<GetTeamResponse>>,
                        response: Response<List<GetTeamResponse>>
                    ) {
                        groupData = response.body()!!
                    }
                })
        binding.groupCount.text=groupData.size.toString()
          */
        binding.groupCount.text = dummyGroupData.size.toString() //더미 데이터


        binding.imageView3.setOnClickListener {
            val i = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(i)
        }


        return binding.root
    }
}

