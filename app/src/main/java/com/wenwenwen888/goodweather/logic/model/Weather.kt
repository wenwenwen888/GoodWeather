package com.wenwenwen888.goodweather.logic.model

/**
 * Created by won on 2021/4/30.
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)
