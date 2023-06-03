package com.example.mtot.retrofit2

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.converter.scalars.ScalarsConverterFactory

val baseUrl = "http://nas.hoony.me:7980"
var accessTokenString = ""

fun getLoginInterface(): LoginInterface {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(loginOkHttpClient())
        .build()

    return retrofit.create(LoginInterface::class.java)
}

fun getRetrofitInterface(): RetrofitInterface {
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    var retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(retrofitOkHttpClient())
        .build()

    return retrofit.create(RetrofitInterface::class.java)
}

fun loginOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder.followRedirects(false)
    builder.followSslRedirects(false)
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    return builder.addInterceptor(logging).build()
}

fun retrofitOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    return builder.addInterceptor(logging).addInterceptor(AddedTokenRequest(accessTokenString!!))
        .build()
}

fun setAccessToken(str: String) {
    accessTokenString = str
}

class AddedTokenRequest(val localToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val tokenRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer " + accessTokenString)
            .addHeader("Content-Type", "application/json").build()
        Log.d("hello", "Bearer " + accessTokenString)

        return chain.proceed(tokenRequest)
    }
}