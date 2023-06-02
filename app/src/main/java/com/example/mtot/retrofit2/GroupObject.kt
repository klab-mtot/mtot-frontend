package com.example.mtot.retrofit2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GroupObject {

    var retrofit = Retrofit.Builder()
        .baseUrl("https://nas.hoony.me:7980")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var groupInterface: GroupInterface = retrofit.create(GroupInterface::class.java)

}