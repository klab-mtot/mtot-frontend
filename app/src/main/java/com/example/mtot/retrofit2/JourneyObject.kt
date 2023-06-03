package com.example.mtot.retrofit2

import com.example.mtot.retrofit2.FriendObject.accessToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JourneyObject {

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

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    var retrofit = Retrofit.Builder()
        .baseUrl("http://nas.hoony.me:7980")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    var journeyInterface: JourneyInterface = retrofit.create(JourneyInterface::class.java)

}