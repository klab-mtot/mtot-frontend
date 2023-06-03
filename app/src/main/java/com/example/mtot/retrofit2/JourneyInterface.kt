package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.GET

interface JourneyInterface {
    @GET("http://nas.hoony.me:7980/journey")
    fun requestJourneyData(): Call<ArrayList<JourneyData>>

}