package com.example.mtot.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.HamburgerItemInfo
import com.example.mtot.R
import com.example.mtot.databinding.FragmentPostHamburgerBinding
import com.example.mtot.retrofit2.getRetrofitInterface

class PostHamburgerFragment : Fragment() {
    lateinit var binding: FragmentPostHamburgerBinding
    lateinit var adapter: PostHamburgerAdapter
    var postHamburgerDataList = ArrayList<HamburgerItemInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostHamburgerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        adapter = PostHamburgerAdapter(postHamburgerDataList)
        binding.rvPostHamburger.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPostHamburger.adapter = adapter
    }

    fun initData(){
        postHamburgerDataList = arrayListOf(
            HamburgerItemInfo(R.drawable.ic_post_hamburger_journey_detail, "여정 상세"),
            HamburgerItemInfo(R.drawable.ic_post_hamburger_edit_post, "포스트 수정")
        )

        val retrofitInterface = getRetrofitInterface()
//        retrofitInterface.requestJourneyData().enqueue(object: Callback<ArrayList<JourneyData>>{
//            override fun onResponse(
//                call: Call<ArrayList<JourneyData>>,
//                response: Response<ArrayList<JourneyData>>
//            ) {
//                Log.d("hello", response.toString())
//                if(response.isSuccessful){
//                    mapHamburgerDataList = ArrayList<HamburgerItemInfo>()
//                    val list = response.body()!!.map {
//                        HamburgerItemInfo(0, it.name)
//                    }
//                    mapHamburgerDataList.addAll(list)
//                }
//            }
//
//            override fun onFailure(call: Call<ArrayList<JourneyData>>, t: Throwable) {
//                Log.d("hello", t.message.toString())
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postHamburgerDataList.clear()
    }
}