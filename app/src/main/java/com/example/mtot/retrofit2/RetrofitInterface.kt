package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/login/oauth2/code/google")
    fun requestUrl(@Query("code") code: String): Call<String>


    @GET("/members/all")
    fun requestTestMember(
    ): Call<TestMemberList>
}

interface FriendInterface {

    @GET("http://nas.hoony.me:7980/friendship")
    fun requestFriendData(): Call<FriendData>


    @POST("http://nas.hoony.me:7980/friendship/accept")
    fun addFriend(
        @Body email: String
    ): Call<AddFriend>

}


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


interface JourneyInterface {
    @GET("http://nas.hoony.me:7980/journey")
    fun requestJourneyData(): Call<ArrayList<JourneyData>>

}
