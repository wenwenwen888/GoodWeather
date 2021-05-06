package com.wenwenwen888.goodweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wenwenwen888.goodweather.logic.Repository
import com.wenwenwen888.goodweather.logic.model.Location

/**
 * Created by won on 2021/4/30.
 */
class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        Repository.refreshWeather(it.lng, it.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

}