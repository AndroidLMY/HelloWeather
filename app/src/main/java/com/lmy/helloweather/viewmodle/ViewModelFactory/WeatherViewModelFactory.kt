package com.lmy.helloweather.viewmodle.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lmy.helloweather.viewmodle.WeatherViewModle

/**
 * @author
 * @功能:
 * @Creat 2020/11/6 3:19 PM
 * @Compony 465008238@qq.com
 */


class WeatherViewModelFactory(val lon: String, val lat: String, val placname: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModle(lon, lat, placname) as T
    }
}