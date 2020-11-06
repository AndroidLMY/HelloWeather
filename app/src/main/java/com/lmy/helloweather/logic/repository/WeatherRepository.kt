package com.lmy.helloweather.logic.repository

import com.lmy.helloweather.base.BaseRepository
import com.lmy.helloweather.model.Result
import com.lmy.helloweather.model.Weather
import com.lmy.helloweather.model.WeatherRealtime
import com.lmy.helloweather.logic.network.api.WeatherApi
import com.lmy.helloweather.logic.network.RetrofitHelp
import com.lmy.helloweather.model.WeatherList
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * @功能:
 * 这里把Repository请求接口和ViewMolde获取数据进行分离
 * 这样的好处是当viewMolde中返回得数据需要两个接口合并的时候
 * 只需要单独写个Repository方法进行处理即可
 * @User Lmy
 * @Creat 2020/7/30 10:32
 * @Compony 永远相信美好的事情即将发生
 */
object WeatherRepository : BaseRepository() {
    /**
     *获取最近天气预报
     *实时天气预报数据
     *进行合并
     */
    suspend fun getWeatherAll(lng: String, lat: String): Result<Weather> {
        return safeApiCall(
            call = {
                lateinit var weather: Weather
                try {
                    coroutineScope {
                        var data1 = async {
                            RetrofitHelp.creat<WeatherApi>().getRealtimeWeather(lng, lat)
                        }
                        var data2 = async {
                            RetrofitHelp.creat<WeatherApi>().getDailyWeather(lng, lat)
                        }
                        weather = Weather(
                            data1.await().result.realtime,
                            data2.await().result.daily
                        )
                        Result.Success(weather)
                    }
                } catch (e: Exception) {
                    Result.Error(e)
                }
            },
            errorMessage = "获取最近天气信息失败"
        )
    }

    /**
     *获取实时天气的数据
     *ViewModel层调用方法
     */
    suspend fun getRealtimeWeather(lng: String, lat: String): Result<WeatherRealtime> {
        return safeApiCall(
            call = { request_getRealtimeWeather(lng, lat) },
            errorMessage = "获取天气信息失败"
        )
    }

    /**
     * 请求调用实时天气的接口数据并返回
     */
    suspend fun request_getRealtimeWeather(lng: String, lat: String): Result<WeatherRealtime> {
        val weatherRealtime = RetrofitHelp.creat<WeatherApi>().getRW_DataResponse(lng, lat)
        return executeResponse(weatherRealtime)
    }

    /**
     *获取未来天气列表的数据
     *ViewModel层调用方法
     */
    suspend fun getDailyWeather(lng: String, lat: String): Result<WeatherList> {
        return safeApiCall(
            call = { request_getDailyWeather_(lng, lat) },
            errorMessage = "获取天气信息失败"
        )
    }

    /**
     * 请求调用未来天气接口并返回
     */
    suspend fun request_getDailyWeather_(lng: String, lat: String): Result<WeatherList> {
        val dailyWeather = RetrofitHelp.creat<WeatherApi>().getDW_DataResponse(lng, lat)
        return executeResponse(dailyWeather)
    }


}