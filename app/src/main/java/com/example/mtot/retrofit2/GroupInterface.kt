package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GroupInterface {

    @GET("/teams")
    fun getTeams(
    ): Call<List<GetTeamResponse>>

    @GET("/friendship")
    fun getFriends(
    ): Call<List<GetFriendResponse>>

    @POST("/journey")
    fun addJourney(
        @Body journeyRequest: AddJourneyRequest
    ): Call<AddJourneyResponse>

    @POST("/teams")
    fun addTeam(
        @Body teamRequest: AddTeamRequest
    ): Call<AddTeamResponse>

}