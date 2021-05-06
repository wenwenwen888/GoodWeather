package com.wenwenwen888.goodweather.logic.network

import com.wenwenwen888.goodweather.GoodWeatherApplication
import com.wenwenwen888.goodweather.logic.model.DailyResponse
import com.wenwenwen888.goodweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by won on 2021/4/30.
 */
interface WeatherService {

    @GET("v2.5/${GoodWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    @GET("v2.5/${GoodWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>

}