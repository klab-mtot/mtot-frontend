package com.example.mtot.retrofit2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun saveUserId(context: Context, id: String){
    val spf = context.getSharedPreferences("MYspf", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()
    editor.putString("userid", id)
    editor.apply()
}

fun getUserId(context: Context): String{
    val spf = context.getSharedPreferences("MYspf", AppCompatActivity.MODE_PRIVATE)
    return spf.getString("userId", "default-user")!!
}

fun setMemberId(context: Context, id: Int){
    val spf = context.getSharedPreferences("member_spf", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()
    editor.putInt("memberId", id)
    editor.apply()
}

fun getMemberId(context: Context): Int{
    val spf = context.getSharedPreferences("member_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getInt("memberId", 1)!!

}