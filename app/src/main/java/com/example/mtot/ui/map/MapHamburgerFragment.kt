package com.example.mtot.ui.map

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.HamburgerItemInfo
import com.example.mtot.databinding.FragmentMapHamburgerBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.ui.post.MapHamburgerAdapter
import retrofit2.Response

class MapHamburgerFragment : Fragment() {
    lateinit var binding : FragmentMapHamburgerBinding
    lateinit var adapter: MapHamburgerAdapter
    var mapHamburgerDataList = ArrayList<HamburgerItemInfo>()

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
        binding.rvMapHamburger.adapter = adapter
    }


    fun initData(){
        Log.d("hello", "abc")
/*
        val retrofitInterface = getRetrofitInterface()
        retrofitInterface.requestJourneyData().enqueue(object: retrofit2.Callback<List<JourneyData>>{
            override fun onResponse(
                call: retrofit2.Call<List<JourneyData>>,
                response: Response<List<JourneyData>>
            ) {
                if(response.isSuccessful){
                    Log.d("hello", response.toString())
                    mapHamburgerDataList = ArrayList<HamburgerItemInfo>()
                    val list = response.body()!!.map {
                        HamburgerItemInfo(0, it.name)
                    }
                    mapHamburgerDataList.addAll(list)
                }
                Log.d("hello", response.toString())

            }

            override fun onFailure(call: retrofit2.Call<List<JourneyData>>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

        })
*/

//        mapHamburgerDataList = arrayListOf(
//            HamburgerItemInfo(0, "여정1"),
//            HamburgerItemInfo(0, "여정2"),
//            HamburgerItemInfo(0, "여정3"),
//            HamburgerItemInfo(0, "여정4"),
//            HamburgerItemInfo(0, "여정5")
//        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapHamburgerDataList.clear()
    }

}