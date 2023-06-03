package com.example.mtot.retrofit2

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import okhttp3.CertificatePinner

data class LoginData(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("email") val email: String
)

data class TestMember(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
)

data class TestMemberList(
    @SerializedName("members") val members: List<TestMember>
)

data class Pin(
    @SerializedName("pinId") val pinId: Int,
    @SerializedName("location") val location: Location
)

data class Location(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)

data class Post(
    @SerializedName("journeyId") val journeyId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("article") val article: String
)

data class JourneyData(
    @SerializedName("journeyId") val journeyId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("post") val post: String,
    @SerializedName("pins") val pins: List<Pin>
)
data class JourneysData(
    @SerializedName("journeys") val journeys: List<JourneyData>,
)



data class SpecificJourneyData(
    @SerializedName("journeyId") val journeyId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("pinData") val pinData: PinData,
    @SerializedName("postData") val postData: PostData
)

data class GetTeamResponse(
    @SerializedName("teamId") val teamId: Int,
    @SerializedName("teamName") val teamName: String
)

data class GetTeamsResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("teamList") val teamList: List<GetTeamResponse>
)

data class GetFriendResponse(
    @SerializedName("friendshipId") val teamId: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class AddJourneyResponse(
    @SerializedName("id") val journeyId: Int
)

data class AddJourneyRequest(
    @SerializedName("journeyName") val journeyName: String,
    @SerializedName("teamId") val teamId: Int
)

data class AddTeamRequest(
    @SerializedName("teamName") val teamName: String,
    @SerializedName("memberList") val memberList: List<AddMember>
)

data class AddTeamResponse(
    @SerializedName("teamId") val teamId: Int
)

data class AddMember(
    @SerializedName("memberId") val memberId: Int,
)

data class PinData(
    val pinName: String
)

data class PostData(
    val postName: String
)


data class FriendData(
    @SerializedName("friendshipId") val friendshipId: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class AddFriend(
    @SerializedName("friendshipId") val friendshipId: Int
)

data class CalendarPhotoDay(
    @SerializedName("day") val day: Int,
    @SerializedName("url") val url: String
)

data class CalendarPhotoMonth(
    @SerializedName("dayList") val dayList: List<CalendarPhotoDay>
)