package com.example.mtot.retrofit2

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitObject {

    var accessToken:String?=null

    private val tokenInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", accessToken.toString())
            .build()

        chain.proceed(modifiedRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .build()

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    var retrofit = Retrofit.Builder()
        .baseUrl("http://nas.hoony.me:7980")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    var friendInterface: FriendInterface = retrofit.create(FriendInterface::class.java)

}


object GroupObject {

    var retrofit = Retrofit.Builder()
        .baseUrl("https://nas.hoony.me:7980")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var groupInterface: GroupInterface = retrofit.create(GroupInterface::class.java)

}



object LoginObject {

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
//    val httpClient = OkHttpClient.Builder()
//        .addInterceptor(RedirectInterceptor())
//        .build()

    var retrofit = Retrofit.Builder()
        .baseUrl("http://nas.hoony.me:7980")
//        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(okHttpClient())
        .build()
    var retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

}

fun okHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder.followRedirects(false)
    builder.followSslRedirects(false)
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return builder.addInterceptor(logging).build()
}



object JourneyObject {

    private val tokenInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", RetrofitObject.accessToken.toString())
            .build()

        chain.proceed(modifiedRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .build()

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    var retrofit = Retrofit.Builder()
        .baseUrl("http://nas.hoony.me:7980")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    var journeyInterface: JourneyInterface = retrofit.create(JourneyInterface::class.java)

}