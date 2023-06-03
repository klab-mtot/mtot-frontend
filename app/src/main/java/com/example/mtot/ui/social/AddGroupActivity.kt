package com.example.mtot.ui.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
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
    lateinit var recyclerDataList : ArrayList<String>
    lateinit var memberList : ArrayList<GetFriendResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){

        recyclerDataList = ArrayList<String>()

        rvAdapter = SelectedMemberAdapter(recyclerDataList)
        binding.rvSelectedMembers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAdapter.OnItemClickListener = object: SelectedMemberAdapter.onItemClickListener {
            override fun onItemClicked(position: Int) {
                val str = rvAdapter.items[position]
                rvAdapter.removeItem(position)
            }
        }
        binding.rvSelectedMembers.adapter = rvAdapter

        binding.editTextMemberSearch.setOnKeyListener { view, keycode, keyEvent ->
            if(keyEvent.action == KeyEvent.ACTION_DOWN && keycode == KEYCODE_ENTER){
                addList()
            }
            true
        }

        binding.ivAddGroupBack.setOnClickListener {
            finish()
        }
        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }
    }

    fun addList(){
        val input = binding.editTextMemberSearch.text.toString()

    }
}