package com.example.mtot.ui.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtot.databinding.ActivityAddGroupBinding
import com.example.mtot.retrofit2.GetFriendResponse

class AddGroupActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddGroupBinding
    lateinit var rvAdapter : SelectedMemberAdapter
    lateinit var spAdapter : ArrayAdapter<String>
    lateinit var recyclerDataList : ArrayList<String>
    lateinit var spinnerDataList : ArrayList<String>
    var nothingSelected = true
    lateinit var memberList : ArrayList<GetFriendResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){

        /* 친구 목록 불러오기 */
//        groupInterface.getFriends().enqueue(object: Callback<List<GetFriendResponse>> {
//            override fun onResponse(
//                call: Call<List<GetFriendResponse>>,
//                response: Response<List<GetFriendResponse>>
//            ) {
//                Log.d("hello", response.toString())
//                if(response.isSuccessful){
//                    memberList = ArrayList<GetFriendResponse>()
//                    memberList.addAll(response.body()!!)
//                    val nameList = response.body()!!.map {
//                        it.name
//                    }.toList()
//                    spinnerDataList = ArrayList<String>()
//                    spinnerDataList.addAll(nameList)
//                }
//            }
//
//            override fun onFailure(call: Call<List<GetFriendResponse>>, t: Throwable) {
//                Log.d("hello", t.message.toString())
//            }
//
//        })

        spinnerDataList = arrayListOf(
            "null",
            "그룹1",
            "그룹2",
            "그룹3"
        )

        recyclerDataList = ArrayList<String>()

        spAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList)
        binding.spAddgroupGroupSelect.adapter = spAdapter
        binding.spAddgroupGroupSelect.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 != 0){
                    val str = spinnerDataList[p2]
                    spinnerDataList.removeAt(p2)
                    rvAdapter.addItem(str)
                    spAdapter.notifyDataSetChanged()
                    binding.spAddgroupGroupSelect.setSelection(0)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.spAddgroupGroupSelect.setSelection(0)
            }
        }

        rvAdapter = SelectedMemberAdapter(recyclerDataList)
        binding.rvSelectedMembers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAdapter.OnItemClickListener = object: SelectedMemberAdapter.onItemClickListener {
            override fun onItemClicked(position: Int) {
                val str = rvAdapter.items[position]
                spinnerDataList.add(str)
                rvAdapter.removeItem(position)
                spAdapter.notifyDataSetChanged()
                binding.spAddgroupGroupSelect.setSelection(0)
            }
        }
        binding.rvSelectedMembers.adapter = rvAdapter


        binding.ivAddGroupBack.setOnClickListener {
            finish()
        }
        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }

//        binding.button.setOnClickListener {
//            val requestBody = AddTeamRequest(
//                binding.editTextGroupName.text.toString(),
//                memberList.filter {
//                    spinnerDataList.contains(it.name)
//                }.map {
//                    AddMember(it.memberId)
//                }
//            )
//            groupInterface.addTeam(requestBody).enqueue(object: Callback<AddTeamResponse>{
//                override fun onResponse(
//                    call: Call<AddTeamResponse>,
//                    response: Response<AddTeamResponse>
//                ) {
//                    Log.d("hello", response.toString())
//                    finish()
//                }
//
//                override fun onFailure(call: Call<AddTeamResponse>, t: Throwable) {
//                    Log.d("hello", t.message.toString())
//                }
//
//            })
//        }


    }
}