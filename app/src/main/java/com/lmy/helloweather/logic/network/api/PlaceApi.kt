package com.lmy.helloweather.logic.network.api

import com.lmy.helloweather.WeatherApplication
import com.lmy.helloweather.logic.model.PlaceResponse
import com.lmy.helloweather.logic.model.Weather
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Lmy
 * @功能: 这里是调用 搜索城市的接口Api
 * @Creat 2020/5/21 9:59
 * @Compony 永远相信美好的事物即将发生
 */
interface PlaceApi {
    @GET("v2/place?token=${WeatherApplication.TOKEN}&lang=zh_CN")
    fun serchPlace(@Query("query") query: String): Call<PlaceResponse>


    @GET("v2/place?token=${WeatherApplication.TOKEN}&lang=zh_CN")
    fun serchPlace2(@Query("query") query: String): Deferred<PlaceResponse>



}