package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.lang.reflect.Member

interface ExampleRetrofitInterface {


    @POST("/members")
    fun signUp(
        @Body member : Member
    ): Call<MemberResponse>

    @GET("/members/{id}")
    fun getMember(
        @Path("id") id: Int
    ): Call<MemberResponse>



}