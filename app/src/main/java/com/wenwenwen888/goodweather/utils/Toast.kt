package com.wenwenwen888.goodweather.utils

import android.widget.Toast
import com.wenwenwen888.goodweather.GoodWeatherApplication

/**
 * Created by won on 2021/4/29.
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(GoodWeatherApplication.context, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(GoodWeatherApplication.context, this, duration).show()
}