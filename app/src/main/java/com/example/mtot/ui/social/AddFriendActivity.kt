package com.example.mtot.ui.social

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import com.example.mtot.databinding.ActivityAddFriendBinding
import com.example.mtot.retrofit2.AddFriend
import com.example.mtot.retrofit2.FriendEmailData
import com.example.mtot.retrofit2.getRetrofitInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddFriendBinding
    lateinit var addFriend: AddFriend
    var alertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init() {

        binding.ivAddFriendBack.setOnClickListener {
            finish()
        }

        binding.buttonAddFriend.setOnClickListener {

            val retrofitInterface = getRetrofitInterface()
            val friendEmail = binding.editTextText.text.toString()
            Log.d("hello", friendEmail)

            retrofitInterface.addFriend(FriendEmailData(friendEmail)).enqueue(object : Callback<AddFriend> {
                override fun onResponse(call: Call<AddFriend>, response: Response<AddFriend>) {
                    Log.d("hello", response.body().toString())
                }


//                    if (response.isSuccessful) {
//                        addFriend = response.body()!!
//                        val dialog = AlertDialog.Builder(this@AddFriendActivity)
//                            .setTitle("친구 추가 요청 발송했습니다")
//                            .setMessage("id: " + addFriend.toString())
//                            .setPositiveButton("확인") { dialog, _ ->
//                                dialog.dismiss() // Dismiss the dialog before finishing the activity
//                                finish()
//                            }
//                            .create()
//                        alertDialog = dialog
//                        alertDialog?.show()
//                    } else {
//                        val dialog = AlertDialog.Builder(this@AddFriendActivity)
//                            .setTitle("아이디가 없습니다")
//                            .setPositiveButton("확인") { dialog, _ ->
//                                dialog.dismiss() // Dismiss the dialog before finishing the activity
//                                binding.editTextText.text.clear()
//                            }
//                            .create()
//                        alertDialog = dialog
//                    }
//                }

                override fun onFailure(call: Call<AddFriend>, t: Throwable) {
                    Log.d("hello", t.message.toString())
                }
            })
        }

        val callback = onBackPressedDispatcher.addCallback {
            finish()
        }
    }

}