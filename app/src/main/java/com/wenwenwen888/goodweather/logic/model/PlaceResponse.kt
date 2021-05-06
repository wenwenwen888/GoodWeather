package com.wenwenwen888.goodweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Created by won on 2021/4/27.
 */
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)