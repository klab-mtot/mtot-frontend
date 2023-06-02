package com.example.mtot.retrofit2

import android.util.Log
import retrofit2.Call
import retrofit2.Response


//그냥 예제니까 빨간줄 무시 ㄱㄱ

//fun setUserName(id: Int) {
//    val memberService = getRetrofit().create(RetrofitInterface::class.java)
//    memberService.getMember(id).enqueue(object : retrofit2.Callback<MemberResponse> {
//        override fun onResponse(
//            call: Call<MemberResponse>,
//            response: Response<MemberResponse>
//        ) {
//            if (response.isSuccessful) {
//                Log.d("SetUserName", response.body().toString())
//                username = response.body()!!.result.name
//                binding.mTvId.text = username
//            }
//        }
//
//        override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
//            Log.d("SetUserName", t.message.toString())
//        }
//    })
//}