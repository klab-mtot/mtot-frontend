package com.example.mtot.ui.social

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import com.example.mtot.databinding.ActivityAddFriendBinding
import com.example.mtot.retrofit2.AddFriend
import com.example.mtot.retrofit2.getRetrofitInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddFriendBinding
    var addFriend:AddFriend?=null
    var alertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){

        binding.button.setOnClickListener{
            val friendInterface = getRetrofitInterface()
            val friendEmail = binding.editTextText.text.toString()

            val requestBody = JSONObject()
            requestBody.put("receiverEmail", friendEmail)

            friendInterface.addFriend(requestBody.toString()).enqueue(object: Callback<AddFriend>{
                override fun onResponse(call: Call<AddFriend>, response: Response<AddFriend>) {
                    addFriend = response.body()

                    if (addFriend != null) {
                        Log.d("addfriendbyby", addFriend.toString())
                        val dialog = AlertDialog.Builder(this@AddFriendActivity)
                            .setTitle("친구 추가했습니다")
                            .setMessage("id: " + addFriend.toString())
                            .setPositiveButton("확인") { dialog, _ ->
                                dialog.dismiss() // Dismiss the dialog before finishing the activity
                                finish()
                            }
                            .create()
                        alertDialog = dialog
                    } else {
                        val dialog = AlertDialog.Builder(this@AddFriendActivity)
                            .setTitle("아이디가 없습니다")
                            .setPositiveButton("확인") { dialog, _ ->
                                dialog.dismiss() // Dismiss the dialog before finishing the activity
                                binding.editTextText.text.clear()
                            }
                            .create()
                        alertDialog = dialog
                    }

                    alertDialog?.show()
                }

                override fun onFailure(call: Call<AddFriend>, t: Throwable) {
                    Log.d("errorbyby", t.message.toString())
                }

            })
        }


//        binding.imageView.setOnClickListener {

//            val friendInterface = getRetrofitInterface()
//            val friendEmail=binding.editTextText.toString()
//            friendInterface.addFriend(friendEmail).enqueue(object : Callback<AddFriend> {
//                override fun onFailure(call: Call<AddFriend>, t: Throwable) {
//                    t.message?.let { it1 -> Log.e("FRIEND", it1) }
//                    val dialog = AlertDialog.Builder(this@AddFriendActivity)
//                    dialog.setTitle("에러")
//                    dialog.setMessage("호출실패했습니다.")
//                    dialog.create().show()
//                }
//
//                override fun onResponse(call: Call<AddFriend>, response: Response<AddFriend>) {
//                    addFriend = response.body()
//                    Log.d("FRIEND", "friendshipId : " + addFriend)
//                    val dialog = AlertDialog.Builder(this@AddFriendActivity)
//                    dialog.setTitle(addFriend.toString())
//                    dialog.setMessage(addFriend.toString())
//                    dialog.create().show()
//                }
//            })
//            finish()
//        }
        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }
    }

}