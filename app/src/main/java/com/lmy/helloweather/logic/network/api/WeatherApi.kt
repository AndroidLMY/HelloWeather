package com.lmy.helloweather.logic.network.api

import com.lmy.helloweather.WeatherApplication
import com.lmy.helloweather.model.DataResponse
import com.lmy.helloweather.model.DataWeatherResponse
import com.lmy.helloweather.model.WeatherList
import com.lmy.helloweather.model.WeatherRealtime
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 14:28
 * @Compony 永远相信美好的事物即将发生
 */
interface WeatherApi {
    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    suspend fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): WeatherRealtime


    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    suspend fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): WeatherList


    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    suspend fun getRW_DataResponse(@Path("lng") lng: String, @Path("lat") lat: String): DataResponse<WeatherRealtime>


    @GET("v2.5/${WeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    suspend fun getDW_DataResponse(@Path("lng") lng: String, @Path("lat") lat: String): DataResponse<WeatherList>


}