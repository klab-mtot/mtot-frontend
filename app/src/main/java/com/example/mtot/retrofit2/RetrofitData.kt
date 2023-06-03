package com.example.mtot.retrofit2

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("email") val email: String
)

data class TestMember(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
)

data class TestMemberList(
    @SerializedName("members") val members : List<TestMember>
)


data class JourneyData(
    @SerializedName("journeyId") val journeyId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("pinLocation") val pinLocation: LatLng,
    @SerializedName("image") val image: Bitmap
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

data class GetFriendResponse(
    @SerializedName("friendshipId") val teamId: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class AddJourneyResponse(
    @SerializedName("journeyId") val journeyId: String
)

data class AddJourneyRequest(
    @SerializedName("journeyId") val journeyName: String,
    @SerializedName("teamId") val teamId: Int
)

data class AddTeamRequest(
    @SerializedName("teamName") val teamName: String,
    @SerializedName("memberList") val memberList : List<AddMember>
)

data class AddTeamResponse(
    @SerializedName("teamId") val teamId : Int
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