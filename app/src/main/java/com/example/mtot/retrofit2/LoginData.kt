package com.example.mtot.retrofit2

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

