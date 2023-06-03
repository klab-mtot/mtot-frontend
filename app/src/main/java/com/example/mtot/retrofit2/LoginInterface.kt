package com.example.mtot.retrofit2

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginInterface {

    @GET("/login/oauth2/code/google")
    fun requestUrl(@Query("code") code: String): Call<String>

    @GET("/members/all")
    fun requestTestMember(
    ): Call<TestMemberList>

}
