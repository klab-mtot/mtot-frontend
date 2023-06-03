package com.example.mtot.retrofit2

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET

interface LoginInterface {

    @GET("/login/oauth")
    fun requestUrl(
    ): Call<String>

    @GET("/members/all")
    fun requestTestMember(
    ): Call<TestMemberList>

}
