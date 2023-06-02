package com.example.mtot.retrofit2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.lang.reflect.Member

interface FriendInterface {

    @GET("http://nas.hoony.me:7980/friendship")
    fun requestFriendData(): Call<FriendData>


    @POST("http://nas.hoony.me:7980/friendship/accept")
    fun addFriend(@Body email:String
    ): Call<AddFriend>

}