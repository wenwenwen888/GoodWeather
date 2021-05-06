package com.wenwenwen888.goodweather.logic

import androidx.lifecycle.liveData
import com.wenwenwen888.goodweather.logic.dao.PlaceDao
import com.wenwenwen888.goodweather.logic.model.Place
import com.wenwenwen888.goodweather.logic.model.Weather
import com.wenwenwen888.goodweather.logic.network.GoodNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Created by won on 2021/4/29.
 */
object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = GoodNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        //coroutineScope协程作用域，async函数需要在协程作用域才能调用
        coroutineScope {
            //在async函数中发起网络请求（两个网络请求是并发的），然后分别调用await函数，就保证两个请求都成功相应才会执行下一步
            val deferredRealtime = async {
                GoodNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                GoodNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

}