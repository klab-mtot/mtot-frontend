package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface LoginInterface {

    @GET("/login/oauth")
    fun requestLogin(
    ): Call<LoginData>

}