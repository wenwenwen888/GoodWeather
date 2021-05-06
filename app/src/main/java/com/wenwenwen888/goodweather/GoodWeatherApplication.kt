package com.wenwenwen888.goodweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by won on 2021/4/27.
 */
class GoodWeatherApplication: Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "dedHOnuJ3NKQYEyt"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}