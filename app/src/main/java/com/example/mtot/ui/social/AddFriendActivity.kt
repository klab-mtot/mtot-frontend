package com.example.mtot.ui.social

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.addCallback
import com.example.mtot.databinding.ActivityAddFriendBinding
import com.example.mtot.retrofit2.AddFriend
import com.example.mtot.retrofit2.FriendEmailData
import com.example.mtot.retrofit2.getMyEmail
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
        val retrofitInterface = getRetrofitInterface()

        binding.ivAddFriendBack.setOnClickListener {
            finish()
        }
        val user_id = getMyEmail(this)
        binding.userId.text = user_id

        binding.buttonAddFriend.setOnClickListener {
            val friendEmail = binding.editTextText.text.toString()
            Log.d("hello", friendEmail)

            retrofitInterface.addFriend(FriendEmailData(friendEmail)).enqueue(object : Callback<AddFriend> {
                override fun onResponse(call: Call<AddFriend>, response: Response<AddFriend>) {
                    if (response.isSuccessful) {
                        addFriend = response.body()!!
                        if (addFriend == null) {
                            val dialog = AlertDialog.Builder(this@AddFriendActivity)
                                .setTitle("Friend request failed")
                                .setMessage("Friend ID does not exist")
                                .setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()
                                    binding.editTextText.text.clear()
                                }
                                .create()
                            alertDialog = dialog
                            alertDialog?.show()
                        } else {
                            val message = "Friend request sent\nid: $addFriend"
                            Toast.makeText(this@AddFriendActivity, message, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        val dialog = AlertDialog.Builder(this@AddFriendActivity)
                            .setTitle("Friend request failed")
                            .setMessage("Friend ID does not exist.")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                                binding.editTextText.text.clear()
                            }
                            .create()
                        alertDialog = dialog
                        alertDialog?.show()
                    }
                }
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