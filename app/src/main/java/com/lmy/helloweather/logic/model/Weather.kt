package com.lmy.helloweather.logic.model

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 15:50
 * @Compony 永远相信美好的事物即将发生
 */
data class Weather(
    val weatherRealtime: WeatherRealtime.Realtime,
    val weatherList: WeatherList.Daily
) {
}