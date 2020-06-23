package com.lmy.helloweather.logic

import androidx.lifecycle.liveData
import com.lmy.helloweather.WeatherApplication
import com.lmy.helloweather.logic.dao.PlaceDao
import com.lmy.helloweather.logic.model.PlaceResponse
import com.lmy.helloweather.logic.model.Weather
import com.lmy.helloweather.logic.network.HellWeatherNetWork
import kotlinx.coroutines.*
import retrofit2.await
import retrofit2.http.Query
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext
import kotlin.math.ln

/**
 * @author Lmy
 * @功能: 用于外部获取一个LiveData对象  外部获取的LiveData对象需要用
 * 这里是仓库层用于 判断调用方请求的数据是来自本地数据还是网络数据
 * Transformations.switchMap进行转换
 * @Creat 2020/5/21 10:20
 * @Compony 永远相信美好的事物即将发生
 */
object Repository {
    /**
     * liveData函数可以自动构建并返回一个LiveData对对象
     *
     */
    fun serchPlaces(query: String) = getliveData(Dispatchers.IO) {
        val placeResponse = HellWeatherNetWork.serchPlace(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    /**
     * 请求实时天气接口数据
     */
    fun serchWeatherRealTime(lng: String, lat: String) = getliveData(Dispatchers.IO) {
        val weatherRealtime = HellWeatherNetWork.serchWeatherRealTime(lng, lat)
        if (weatherRealtime.status == "ok") {
            val weather = weatherRealtime.result.realtime
            Result.success(weather)
        } else {
            Result.failure(RuntimeException("response status is ${weatherRealtime.status}"))
        }
    }

    /**
     * 请求最近几天天气的接口
     *
     */
    fun searchWeatherDaily(lng: String, lat: String) = getliveData(Dispatchers.IO) {
        val weatherList = HellWeatherNetWork.serchWeatherDaily(lng, lat)
        if (weatherList.status == "ok") {
            val weather = weatherList.result.daily
            Result.success(weather)
        } else {
            Result.failure(RuntimeException("response status is ${weatherList.status}"))
        }
    }

    /**
     * 记录选中城市
     */
    fun savePlace(place: PlaceResponse.Place) = PlaceDao.savePlace(place)

    /**
     * 获取选中的城市
     */
    fun getSavedPlace() = PlaceDao.getSavedPlace()

    /**
     * 判断是否有城市是被选中
     */
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    /**
     * 同属刷新实时天气和未来几天的天气
     * 这里使用了coroutineScope 和async方法 来使两个接口同步执行
     *async方法是可以返回一个执行结果的 而launch则实现不了
     */
    fun refreshWeather(lng: String, lat: String) = getliveData(Dispatchers.IO) {
        coroutineScope {
            /**
             * 当调用async函数的时候 不会立即执行lambda表达式中的内容
             * 只有当调用async.await()时才会执行
             *
             */
            val realtime = async {
                HellWeatherNetWork.serchWeatherRealTime(lng, lat)
            }
            val daily = async {
                HellWeatherNetWork.serchWeatherDaily(lng, lat)
            }
            val weatherRealTime = realtime.await()
            val weatherDaily = daily.await()
            if (weatherDaily.status == "ok" && weatherRealTime.status == "ok") {
                val weather = Weather(weatherRealTime.result.realtime, weatherDaily.result.daily)
                Result.success(weather)
            } else {
                Result.failure(RuntimeException("response status is ${weatherRealTime.status}${weatherDaily.status}"))
            }
        }
    }

    suspend fun refreshWeather2(lng: String, lat: String): Weather {
        var weather: Weather
        val job = Job()
        val coroutineScope = CoroutineScope(Job())
        //下面两个coroutineScope launch其实是并发的
        val realtime = coroutineScope.async {
            HellWeatherNetWork.serchWeatherRealTime(lng, lat)
        }
        val daily = coroutineScope.async {
            HellWeatherNetWork.serchWeatherDaily(lng, lat)
        }
        val weatherRealTime = realtime.await()
        val weatherDaily = daily.await()
        if (weatherDaily.status == "ok" && weatherRealTime.status == "ok") {
            weather = Weather(weatherRealTime.result.realtime, weatherDaily.result.daily)
        } else {
            throw RuntimeException("response status is ${weatherRealTime.status}${weatherDaily.status}")
        }
        return weather
    }

    /**
     * 对livedata创建进行封装函数进行封装
     *
     */
    private fun <T> getliveData(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}


