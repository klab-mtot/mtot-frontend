package com.example.mtot.retrofit2

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class JourneyData(
    @SerializedName("journeyId") val journeyId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("pinLocation") val pinLocation: LatLng,
    @SerializedName("image") val image: Bitmap
)


data class SpecificJourneyData(
    @SerializedName("journeyId") val journeyId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("pinData") val pinData: PinData,
    @SerializedName("postData") val postData: PostData
)
data class PinData(
    val pinName: String
)

data class PostData(
    val postName: String
)