package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginInterface {
    @GET("/login/oauth2/code/google")
    fun requestUrl(@Query("code") code: String): Call<String>

    @GET("/members/all")
    fun requestTestMember(
    ): Call<TestMemberList>
}

interface RetrofitInterface {

    @GET("/friendship")
    fun requestFriendsData(): Call<FriendsData>

    @POST("/friendship")
    fun addFriend(
        @Body email: FriendEmailData
    ): Call<AddFriend>

    @GET("/teams")
    fun getTeams(
    ): Call<GetTeamsResponse>

    @GET("/friendship")
    fun getFriends(
    ): Call<GetFriendshipResponse>


    @GET("/friendship/pending")
    fun getPendingFriendships(
    ): Call<PendingFriendshipsData>

    @POST("/friendship/reject")
    fun rejectPendingFriendship(
        @Body friendshipId:Int
    ):Call<String>
    @POST("/friendship/reject")
    fun acceptPendingFriendship(
        @Body friendshipId:Int
    ):Call<String>

    @POST("/journey")
    fun addJourney(
        @Body journeyRequest: AddJourneyRequest
    ): Call<AddJourneyResponse>

    @POST("/teams")
    fun addTeam(
        @Body teamRequest: AddTeamRequest
    ): Call<AddTeamResponse>

    @GET("/journey")
    fun requestJourneyData(): Call<JourneysData>

    @GET("/photo/calendarThumbnail")
    fun requestCalendarPhoto(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): Call<CalendarPhotoMonth>

}