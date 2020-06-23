package com.lmy.helloweather.logic.network.api

import com.lmy.helloweather.WeatherApplication
import com.lmy.helloweather.logic.model.WeatherList
import com.lmy.helloweather.logic.model.WeatherRealtime
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 14:28
 * @Compony 永远相信美好的事物即将发生
 */
interface WeatherApi {
    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<WeatherRealtime>

    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<WeatherList>




    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather2(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Deferred<WeatherRealtime>

    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather2(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Deferred<WeatherList>
}