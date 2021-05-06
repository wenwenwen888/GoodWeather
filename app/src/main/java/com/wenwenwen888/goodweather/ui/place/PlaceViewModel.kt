package com.wenwenwen888.goodweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wenwenwen888.goodweather.logic.Repository
import com.wenwenwen888.goodweather.logic.model.Place

/**
 * Created by won on 2021/4/29.
 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) {
        Repository.searchPlaces(it)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavePlace() = Repository.getSavePlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

}