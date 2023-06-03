package com.example.mtot.retrofit2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName

fun saveAccessToken(context: Context, accessToken: String) {
    val pref =
        context.getSharedPreferences("accessToken_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putString("accessToken", accessToken) // 값 넣기
    edit.apply() // 적용하기
}

fun saveMyEmail(context: Context, email: String) {
    val pref = context.getSharedPreferences("myEmail_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putString("email", email) // 값 넣기
    edit.apply() // 적용하기
}

fun saveFriendData(
    context: Context, friendData:String
) {
    val pref = context.getSharedPreferences("FriendData_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putString("FriendData", friendData.toString()) // 값 넣기
    edit.apply() // 적용하기
}

fun getAccessToken(context: Context): String {
    val spf = context.getSharedPreferences("accessToken_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getString("accessToken", 1.toString())!!
}

fun getMyEmail(context: Context): String {
    val spf = context.getSharedPreferences("myEmail_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getString("email", 1.toString())!!
}
