package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GroupInterface {

    @GET("/teams")
    fun getTeams(
    ): Call<List<GroupData>>

}