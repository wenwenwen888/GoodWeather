package com.wenwenwen888.goodweather.ui.weather

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wenwenwen888.goodweather.R
import com.wenwenwen888.goodweather.base.BaseBindingActivity
import com.wenwenwen888.goodweather.databinding.ActivityWeatherBinding
import com.wenwenwen888.goodweather.logic.model.Weather
import com.wenwenwen888.goodweather.logic.model.getSky
import com.wenwenwen888.goodweather.utils.showToast
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : BaseBindingActivity<ActivityWeatherBinding>() {

    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //透明状态栏
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }

        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }

        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(this, Observer {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                "无法成功获取天气信息".showToast()
                it.exceptionOrNull()?.printStackTrace()
            }
            binding.swipeRefresh.isRefreshing = false
        })

        binding.apply {
            swipeRefresh.setColorSchemeColors(resources.getColor(R.color.purple_200))
            refreshWeather()
            swipeRefresh.setOnRefreshListener {
                refreshWeather()
            }

            nowLayout.navBtn.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            drawerLayout.addDrawerListener(object :DrawerLayout.DrawerListener{

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                }

                override fun onDrawerOpened(drawerView: View) {
                }

                override fun onDrawerClosed(drawerView: View) {
                    val manage = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    manage.hideSoftInputFromWindow(drawerView.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
                }

                override fun onDrawerStateChanged(newState: Int) {
                }

            })
        }

    }

    /**
     * 执行刷新天气
     */
    fun refreshWeather(){
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        binding.swipeRefresh.isRefreshing = true
    }

    @SuppressLint("SetTextI18n")
    private fun showWeatherInfo(weather: Weather) {
        binding.apply {
            nowLayout.placeName.text = viewModel.placeName
            val realtime = weather.realtime
            val daily = weather.daily
            //填充now.xml布局中的数据
            nowLayout.currentTemp.text = "${realtime.temperature.toInt()} ℃"
            nowLayout.currentSky.text = getSky(realtime.skycon).info
            nowLayout.currentAQI.text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
            nowLayout.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
            //填充forecast.xml布局中的数据
            forecast.forecastLayout.removeAllViews()
            for (i in daily.skycon.indices) {
                val skycon = daily.skycon[i]
                val temperature = daily.temperature[i]
                val view = LayoutInflater.from(this@WeatherActivity)
                    .inflate(R.layout.forecast_item, forecast.forecastLayout, false)
                val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
                val skyIcon = view.findViewById<ImageView>(R.id.skyIcon)
                val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
                val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)
                //String类型无法直接parse为一个date类型，所以需要先把String字符串，格式化为一个date类型，最后再格式化为你想要的格式
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dateInfo.text = simpleDateFormat.format(simpleDateFormat.parse(skycon.date))
                skyIcon.setImageResource(getSky(skycon.value).icon)
                skyInfo.text = getSky(skycon.value).info
                temperatureInfo.text = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"

                forecast.forecastLayout.addView(view)
            }
            //填充life-index.xml布局中的数据
            val lifeIndex = daily.lifeIndex
            lifeIndexLayout.coldRiskText.text = lifeIndex.coldRisk[0].desc
            lifeIndexLayout.dressingText.text = lifeIndex.dressing[0].desc
            lifeIndexLayout.ultravioletText.text = lifeIndex.ultraviolet[0].desc
            lifeIndexLayout.carWashingText.text = lifeIndex.carWashing[0].desc

            weatherLayout.visibility = View.VISIBLE
        }
    }

}