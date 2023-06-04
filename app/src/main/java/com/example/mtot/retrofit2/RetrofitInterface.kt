package com.example.mtot.retrofit2

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import android.provider.ContactsContract.Contacts.Photo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
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
        @Body friendshipId: Int
    ): Call<ResponseFriendRequestData>

    @POST("/friendship/accept")
    fun acceptPendingFriendship(
        @Body friendshipId: Int
    ): Call<ResponseFriendRequestData>

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

    @GET("/journey/{journey_id}")
    fun requestJourneyData(
        @Path("journey_id") journey_id: Int
    ): Call<JourneyData>

    @GET("/photo/calendarThumbnail")
    fun requestCalendarPhoto(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): Call<CalendarPhotoMonth>

    @GET("/photo/journey/{journeyId}")
    fun getJourneyPhotos(
        @Path("journeyId") journeyId: Int
    ): Call<List<PhotoUrls>>


    @POST("/teams/register")
    fun addMemberToTeam(
        @Body requestBody: RequestAddMemberToTeam
    ): Call<ResponseAddMemberToTeam>

    @POST("/journey/requestPin")
    fun addPinToJourney(
        @Body requestBody: RequestAddPin
    ): Call<ResponseAddPin>

    @Multipart
    @POST("/photo")
    fun addPhotoToPin(
        @Part("pinId") pinId: RequestBody,
        @Part imageList: List<MultipartBody.Part>
    ): Call<ResponseAddPhotoToPin>


    @GET("/photo/journey/{journeyId}")
    fun requestJourneyPhotos(
    ): Call<List<PhotoData>>

    @GET("/photo/pin/{pinId}")
    fun requestPinPhotos(
        @Path("pinId") pinId: Int
    ): Call<RequestPhotos>

    @POST("/post")
    fun addPost(
        @Body requestBody: Post
    ): Call<ResponseAddPost>

    @POST("/post/edit")
    fun EditPost(
        @Body journeyId: Int,
        @Body title: String,
        @Body article: String
    ):Call<Int>

}