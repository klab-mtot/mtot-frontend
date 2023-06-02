package com.example.mtot.retrofit2

import com.google.gson.annotations.SerializedName
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



