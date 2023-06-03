package com.example.mtot.ui.social

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import com.example.mtot.databinding.ActivityAddFriendBinding
import com.example.mtot.retrofit2.AddFriend
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddFriendBinding
    var addFriend:AddFriend?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        binding.imageView.setOnClickListener {

            val friendInterface = getRetrofitInterface()
            val friendEmail=binding.editTextText.toString()
            friendInterface.addFriend(friendEmail).enqueue(object : Callback<AddFriend> {
                override fun onFailure(call: Call<AddFriend>, t: Throwable) {
                    t.message?.let { it1 -> Log.e("FRIEND", it1) }
                    val dialog = AlertDialog.Builder(this@AddFriendActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.create().show()
                }

                override fun onResponse(call: Call<AddFriend>, response: Response<AddFriend>) {
                    addFriend = response.body()
                    Log.d("FRIEND", "friendshipId : " + addFriend)
                    val dialog = AlertDialog.Builder(this@AddFriendActivity)
                    dialog.setTitle(addFriend.toString())
                    dialog.setMessage(addFriend.toString())
                    dialog.create().show()
                }
            })
            finish()
        }
        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }
    }

}