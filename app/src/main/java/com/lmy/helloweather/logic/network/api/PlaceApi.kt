package com.lmy.helloweather.logic.network.api

import com.lmy.helloweather.WeatherApplication
import com.lmy.helloweather.model.DataWeatherResponse
import com.lmy.helloweather.model.Place
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
    suspend fun serchPlace(@Query("query") query: String): DataWeatherResponse<List<Place>>
}