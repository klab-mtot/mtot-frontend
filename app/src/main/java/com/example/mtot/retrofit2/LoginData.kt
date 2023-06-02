package com.example.mtot.retrofit2

import com.google.gson.annotations.SerializedName
data class LoginData(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("email") val email: String
)

