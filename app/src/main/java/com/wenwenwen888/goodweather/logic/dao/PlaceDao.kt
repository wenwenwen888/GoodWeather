package com.wenwenwen888.goodweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.wenwenwen888.goodweather.GoodWeatherApplication
import com.wenwenwen888.goodweather.logic.model.Place

/**
 * Created by won on 2021/5/6.
 */
object PlaceDao {

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = GoodWeatherApplication.context.getSharedPreferences(
        "good_weather", Context.MODE_PRIVATE
    )

}