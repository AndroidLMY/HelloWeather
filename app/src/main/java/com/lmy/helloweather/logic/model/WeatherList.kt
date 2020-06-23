package com.lmy.helloweather.logic.model

import java.util.*

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 14:34
 * @Compony 永远相信美好的事物即将发生
 */

data class WeatherList(
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
        val daily: Daily,
        val primary: Int
    )

    data class Daily(
        val skycon: List<Skycon>,
        val temperature: List<Temperature>,
        val life_index: LifeIndex

    )


    data class LifeIndex(
        val carWashing: List<CarWashing>,
        val coldRisk: List<ColdRisk>,
        val comfort: List<Comfort>,
        val dressing: List<Dressing>,
        val ultraviolet: List<Ultraviolet>
    )


    data class Skycon(
        val date: Date,
        val value: String
    )


    data class Temperature(
        val avg: Double,
        val date: String,
        val max: Double,
        val min: Double
    )


    data class CarWashing(
        val date: String,
        val desc: String,
        val index: String
    )

    data class ColdRisk(
        val date: String,
        val desc: String,
        val index: String
    )

    data class Comfort(
        val date: String,
        val desc: String,
        val index: String
    )

    data class Dressing(
        val date: String,
        val desc: String,
        val index: String
    )

    data class Ultraviolet(
        val date: String,
        val desc: String,
        val index: String
    )


}

