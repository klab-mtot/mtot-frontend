package com.example.mtot.retrofit2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun saveAccessToken(context: Context, accessToken: String) {
    val pref =
        context.getSharedPreferences("accessToken_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putString("accessToken", accessToken) // 값 넣기
    edit.apply() // 적용하기
}
fun getAccessToken(context: Context): String {
    val spf = context.getSharedPreferences("accessToken_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getString("accessToken", 1.toString())!!
}

fun saveMyEmail(context: Context, email: String) {
    val pref = context.getSharedPreferences("myEmail_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putString("email", email) // 값 넣기
    edit.apply() // 적용하기
}

fun getMyEmail(context: Context): String {
    val spf = context.getSharedPreferences("myEmail_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getString("email", 1.toString())!!
}



fun savePostState(context: Context, postState: Boolean) {
    val pref =
        context.getSharedPreferences("postState_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putBoolean("postState_spf", postState) // 값 넣기
    edit.apply() // 적용하기
}

fun getPostState(context: Context): Boolean {
    val spf = context.getSharedPreferences("postState_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getBoolean("postState_spf", false)!!
}

fun saveJourneyId(context: Context, journeyId: Int) {
    val pref =
        context.getSharedPreferences("journeyId_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putInt("journeyId_spf", journeyId) // 값 넣기
    edit.apply() // 적용하기
}

fun getJourneyId(context: Context): Int {
    val spf = context.getSharedPreferences("journeyId_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getInt("journeyId_spf", -1)!!
}

fun saveFriendData(
    context: Context, friendData:String
) {
    val pref = context.getSharedPreferences("FriendData_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putString("FriendData", friendData.toString()) // 값 넣기
    edit.apply() // 적용하기
}


