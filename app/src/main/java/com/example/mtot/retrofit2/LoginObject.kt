package com.example.mtot.retrofit2

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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
    var loginInterface: LoginInterface = retrofit.create(LoginInterface::class.java)

}

fun okHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return builder.addInterceptor(logging).build()
}