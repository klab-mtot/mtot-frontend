package com.example.mtot.retrofit2

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Multipart

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
    @SerializedName("post") val post: Post,
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

data class GetTeamResponse( //타입이 list이어야 함
    @SerializedName("teamId") val teamId: Int,
    @SerializedName("teamName") val teamName: String
)
data class GetTeamsResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("teamList") val teamList: List<GetTeamResponse>
)

data class GetFriendResponse( //타입이 list이어야 함
    @SerializedName("friendshipId") val teamId: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class GetFriendshipResponse(
    @SerializedName("friendships") val friendships: List<GetFriendResponse>
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
)

data class AddTeamResponse(
    @SerializedName("teamId") val teamId: Int
)

data class AddMember(
    @SerializedName("memberId") val memberId: Int,
)

data class PinData(
    @SerializedName("pinName") val pinName: String
)

data class PostData(
    @SerializedName("postName") val postName: String
)


data class FriendData(
    @SerializedName("friendshipId") val friendshipId: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class FriendsData(
    @SerializedName("friendships") val friendships: List<FriendData>,
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

data class PhotoUrls(
    @SerializedName("url") val url: String
)

data class ResponsePhotoUrls(
    @SerializedName("photoUrls") val photoUrls: List<String>
)

data class FriendEmailData(
    @SerializedName("receiverEmail") val receiverEmail: String
)

data class RequestAddMemberToTeam(
    @SerializedName("teamId") val teamId: Int,
    @SerializedName("memberEmail") val memberEmail: String
)

data class ResponseAddMemberToTeam(
    @SerializedName("teamId") val teamId: Int
)

data class PendingFriendshipsData(
    @SerializedName("pendingFriendships") val pendingFriendships: List<PendingFriendshipData>
)

data class PendingFriendshipData(
    @SerializedName("friendshipId") val friendshipId: Int,
    @SerializedName("requesterName") val requesterName: String,
    @SerializedName("requesterEmail") val requesterEmail: String
)

data class RequestAddPin(
    @SerializedName("journeyId") val journeyId: Int,
    @SerializedName("location") val location: LocationData
)

data class LocationData(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)

data class ResponseAddPin(
    @SerializedName("pinId") val pinId: Int
)

data class RequestPhotos(
    @SerializedName("photoUrls") val photoUrls: List<String>
)

data class  PhotoData(
    @SerializedName("url") val url: String
)

data class ResponseAddPhotoToPin(
    @SerializedName("photoIds") val photoIds: List<Int>,
    @SerializedName("photosUrl") val photosUrl: List<String>
)

data class ResponseFriendRequestData(
    @SerializedName("success") val success: Boolean
)

data class ResponseAddPost(
    @SerializedName("postId") val postId: Int
)