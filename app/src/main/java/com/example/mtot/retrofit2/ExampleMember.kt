package com.example.mtot.retrofit2

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("email_id") val email_id: String,
    @SerializedName("password") val password: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: String,
    @SerializedName("birth") val birth: String
)

data class MemberResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: MemberResult
)

data class MemberResult(
    @SerializedName("id") val id: Int,
    @SerializedName("email_id") val email_id: String,
    @SerializedName("name") val name: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("gender") val gender: String
)
