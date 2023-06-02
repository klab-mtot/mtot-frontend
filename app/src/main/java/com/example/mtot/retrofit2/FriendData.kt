package com.example.mtot.retrofit2

import com.google.gson.annotations.SerializedName

data class FriendData(
    @SerializedName("friendshipId") val friendshipId: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class AddFriend(
    @SerializedName("friendshipId") val friendshipId: Int
    )
