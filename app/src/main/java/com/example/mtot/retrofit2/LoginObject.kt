package com.example.mtot.retrofit2

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object LoginObject {

    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()
    var retrofit = Retrofit.Builder()
        .baseUrl("http://nas.hoony.me:7980")
        //.addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    var loginInterface: LoginInterface = retrofit.create(LoginInterface::class.java)

}