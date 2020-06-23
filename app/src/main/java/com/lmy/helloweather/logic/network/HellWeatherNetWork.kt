package com.lmy.helloweather.logic.network

import com.lmy.helloweather.logic.network.api.PlaceApi
import com.lmy.helloweather.logic.network.api.WeatherApi
import com.lmy.helloweather.logic.network.base.RetrofitHelp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author Lmy
 * @功能: 网络层代码
 * @Creat 2020/5/21 10:12
 * @Compony 永远相信美好的事物即将发生
 */
object HellWeatherNetWork {
    private val placeApi = RetrofitHelp.creat<PlaceApi>()
    private val weatherApi = RetrofitHelp.creat<WeatherApi>()

    //搜索城市
    suspend fun serchPlace(query: String) = placeApi.serchPlace(query).await()

    //查询城市实时天气
    suspend fun serchWeatherRealTime(lng: String, lat: String) =
        weatherApi.getRealtimeWeather(lng, lat).await()

    //查询城市未来几天的天气
    suspend fun serchWeatherDaily(lng: String, lat: String) =
        weatherApi.getDailyWeather(lng, lat).await()

    /**
     * 这里使用协程挂起了这个函数
     * suspendCoroutine 关键字用于简化传统的回调机制
     * 这里扩展了Call<T>对象的一个await扩展函数返回一个suspendCoroutine回调机制
     * 用于解析enqueue执行完成以后返回的JSON数据
     * 然后回调给上一层函数
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("返回的body为空"))
                }
            })
        }
    }

}