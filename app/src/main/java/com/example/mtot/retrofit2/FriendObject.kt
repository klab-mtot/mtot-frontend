package com.example.mtot.retrofit2

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.mtot.StartActivity

object FriendObject {

    var accessToken:String?=null

    private val tokenInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", accessToken.toString())
            .build()

        chain.proceed(modifiedRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .build()

    var retrofit = Retrofit.Builder()
        .baseUrl("https://nas.hoony.me:7980")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var friendInterface: FriendInterface = retrofit.create(FriendInterface::class.java)

}