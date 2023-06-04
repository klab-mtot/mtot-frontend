package com.example.mtot.retrofit2

import android.provider.ContactsContract.Contacts.Photo
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
    @POST("/friendship/accept")
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
    fun requestJourneysData(): Call<JourneysData>

    @GET("/journey/{journeyId}")
    fun requestJourneyData():Call<JourneyData>

    @GET("/journey/journey_id")
    fun getJourney(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): Call<CalendarPhotoMonth>

    @GET("/photo/calendarThumbnail")
    fun requestCalendarPhoto(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): Call<CalendarPhotoMonth>

    @POST("/teams/register")
    fun addMemberToTeam(
        @Body requestBody: RequestAddMemberToTeam
    ): Call<ResponseAddMemberToTeam>

    @POST("/journey/requestPin")
    fun addPinToJourney(
        @Body requestBody: RequestAddPin
    ): Call<ResponseAddPin>

//    @POST("/photo")
//    fun addPhotoToPin(
//        @Body requestBody: RequestAddPhotoToPin
//    ): Call<RequestAddPhotoToPin>

    @GET("/photo/journey/{journeyId}")
    fun requestJourneyPhotos(
    ):Call<List<PhotoData>>

}