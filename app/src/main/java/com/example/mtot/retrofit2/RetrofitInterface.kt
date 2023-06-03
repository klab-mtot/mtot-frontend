package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitInterface {

    @GET("/login/oauth")
    fun requestUrl(
    ): Call<String>

    @GET("/members/all")
    fun requestTestMember(
    ): Call<TestMemberList>

    @GET("http://nas.hoony.me:7980/friendship")
    fun requestFriendData(): Call<FriendData>


    @POST("http://nas.hoony.me:7980/friendship/accept")
    fun addFriend(@Body email:String
    ): Call<AddFriend>

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

    @GET("http://nas.hoony.me:7980/journey")
    fun requestJourneyData(): Call<ArrayList<JourneyData>>

}
