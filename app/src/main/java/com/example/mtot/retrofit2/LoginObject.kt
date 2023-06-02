package com.example.mtot.retrofit2

import com.example.mtot.retrofit2.FriendObject.retrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginObject {

    val gson: Gson =GsonBuilder()
        .setLenient()
        .create()

    var retrofit = Retrofit.Builder()
        .baseUrl("http://nas.hoony.me:7980")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    var loginInterface: LoginInterface = retrofit.create(LoginInterface::class.java)




}