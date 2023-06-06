package com.example.mtot.ui.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.JourneyDetailActivity
import com.example.mtot.MainActivity
import com.example.mtot.R
import com.example.mtot.databinding.FragmentPostHamburgerBinding
import com.example.mtot.retrofit2.JourneyData
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostHamburgerFragment : Fragment() {
    lateinit var binding: FragmentPostHamburgerBinding
    lateinit var adapter: PostHamburgerAdapter
    var postHamburgerDataList = ArrayList<PostHamburgerItemInfo>()
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
        adapter.OnItemClickListener = object: PostHamburgerAdapter.onItemClickListener {
            override fun onItemClicked(pos: Int) {
                val item = adapter.items[pos]

                when(item.type){
                    0 -> { //여정
                        val journeyId = item.id
                        val i = Intent(requireActivity(), JourneyDetailActivity::class.java)
                        i.putExtra("journeyId", journeyId)
                        startActivity(i)
                    }
                    1 -> { //포스트
                        val journeyId = item.id
                        val i = Intent(requireActivity(), PostDetailActivity::class.java)
                        i.putExtra("journeyId", journeyId)
                        startActivity(i)
                    }
                    2 -> { //핀
                        val pinId = item.id
                        val i = Intent(requireActivity(), PinDetailActivity::class.java)
                        i.putExtra("pinId", pinId)
                        startActivity(i)
                    }
                }
            }
        }
        binding.rvPostHamburger.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPostHamburger.adapter = adapter

        binding.llPostHamburgerStop.setOnClickListener {
            val mainActivity = (requireActivity() as MainActivity)
            mainActivity.stopRecording()
            mainActivity.hidePostHamburgerToolbar()
            mainActivity.postFragment.clearPin()
        }
    }

    fun initData(){
        postHamburgerDataList = arrayListOf(
            PostHamburgerItemInfo(0, getJourneyId(requireContext()), "여정 상세",R.drawable.ic_post_hamburger_journey_detail),
            PostHamburgerItemInfo(1, getJourneyId(requireContext()), "포스트 수정",R.drawable.ic_post_hamburger_edit_post)
        )

        val retrofitInterface = getRetrofitInterface()
        retrofitInterface.requestJourneyData(getJourneyId(requireContext())).enqueue(object: Callback<JourneyData> {
            override fun onResponse(
                call: Call<JourneyData>,
                response: Response<JourneyData>
            ) {
                Log.d("hello", response.body().toString())
                if(response.isSuccessful){
                    postHamburgerDataList.addAll(response.body()!!.pins.map {
                        PostHamburgerItemInfo(2, it.pinId, it.pinId.toString(),R.drawable.ic_post_hamburger_edit_pin)
                    })
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<JourneyData>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postHamburgerDataList.clear()
    }
}