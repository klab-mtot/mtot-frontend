package com.example.mtot.ui.map

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.HamburgerItemInfo
import com.example.mtot.JourneyDetailActivity
import com.example.mtot.MainActivity
import com.example.mtot.databinding.FragmentMapHamburgerBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.JourneysData
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.ui.post.MapHamburgerAdapter
import com.example.mtot.ui.social.FriendRequestActivity
import com.example.mtot.ui.social.FriendRequestAdapter
import com.example.mtot.ui.social.FriendRequestListInfo
import retrofit2.Response

class MapHamburgerFragment : Fragment() {
    lateinit var binding : FragmentMapHamburgerBinding
    lateinit var adapter: MapHamburgerAdapter
    var mapHamburgerDataList=ArrayList<HamburgerItemInfo>()
    lateinit var journeysData: JourneysData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapHamburgerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        adapter = MapHamburgerAdapter(mapHamburgerDataList)
        binding.rvMapHamburger.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        adapter.OnItemClickListener = object : MapHamburgerAdapter.onItemClickListener {
            override fun MapHamburgerButtonClicked(position: Int) {

                val intent = Intent(requireActivity(), JourneyDetailActivity::class.java)
                intent.putExtra("key", journeysData.journeys[position].journeyId)
                requireActivity().startActivity(intent)


            }

        }

        binding.rvMapHamburger.adapter = adapter
    }


    fun initData(){
        val retrofitInterface = getRetrofitInterface()
        retrofitInterface.requestJourneysData().enqueue(object: retrofit2.Callback<JourneysData>{
            override fun onResponse(
                call: retrofit2.Call<JourneysData>,
                response: Response<JourneysData>
            ) {
                Log.d("hello", response.body().toString())
                if(response.isSuccessful){
                    val list = response.body()!!.journeys.map {
                        HamburgerItemInfo(it.journeyId, it.name, 0)
                    }
                    mapHamburgerDataList.addAll(list)
                    adapter.notifyDataSetChanged()

                    journeysData=response.body()!!

                }

            }

            override fun onFailure(call: retrofit2.Call<JourneysData>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapHamburgerDataList.clear()
    }

}