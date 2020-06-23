package com.lmy.helloweather.logic.model

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 14:21
 * @Compony 永远相信美好的事物即将发生
 */
data class WeatherRealtime(
    val api_status: String,
    val api_version: String,
    val lang: String,
    val location: List<Double>,
    val result: Result,
    val server_time: Int,
    val status: String,
    val timezone: String,
    val tzshift: Int,
    val unit: String
) {
    data class Result(
        val primary: Int,
        val realtime: Realtime
    )
    data class Realtime(
        val temperature: Double,
        val skycon: String,
        val air_quality: AirQuality
    )
    data class AirQuality(
        val aqi: Aqi,
        val co: Double,
        val description: Description,
        val no2: Double,
        val o3: Double,
        val pm10: Double,
        val pm25: Double,
        val so2: Double
    )
    data class Aqi(
        val chn: Double,
        val usa: Double
    )
    data class Description(
        val chn: String,
        val usa: String
    )
}

