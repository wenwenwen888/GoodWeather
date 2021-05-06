package com.wenwenwen888.goodweather.logic.network

import com.wenwenwen888.goodweather.GoodWeatherApplication
import com.wenwenwen888.goodweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by won on 2021/4/29.
 */
interface PlaceService {

    @GET("v2/place?token=${GoodWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}