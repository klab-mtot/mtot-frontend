package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.GET

interface LoginInterface {

    @GET("http://nas.hoony.me:7980/login/oauth")
    fun requestLogin(
    ): Call<String>

}