package com.wenwenwen888.goodweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Created by won on 2021/4/30.
 */
data class DailyResponse(
    val result: Result,
    val status: String, // ok
) {
    data class Result(
        val daily: Daily,
    )

    data class Daily(
        @SerializedName("life_index") val lifeIndex: LifeIndex,
        val skycon: List<Skycon>,
        val temperature: List<Temperature>,
    )

    data class LifeIndex(
        val carWashing: List<LifeDescription>,
        val coldRisk: List<LifeDescription>,
        val dressing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>
    )

    data class LifeDescription(
        val desc: String, // 适宜
    )

    data class Skycon(
        val date: String, // 2021-04-30T00:00+08:00
        val value: String // PARTLY_CLOUDY_DAY
    )

    data class Temperature(
        val max: Double, // 30.0
        val min: Double // 20.0
    )

}