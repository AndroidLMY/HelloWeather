package com.lmy.helloweather.ui.basequick

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmy.helloweather.base.BaseViewModel
import com.lmy.helloweather.logic.Repository
import com.lmy.helloweather.logic.model.PlaceResponse
import com.lmy.helloweather.logic.model.Weather
import com.lmy.helloweather.logic.network.HellWeatherNetWork
import com.lmy.helloweather.logic.network.api.PlaceApi
import com.lmy.helloweather.logic.network.api.WeatherApi
import com.lmy.helloweather.logic.network.base.RetrofitHelp
import com.lmy.helloweather.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 16:01
 * @Compony 永远相信美好的事物即将发生
 */
class BaseQuickAdapterViewModle : BaseViewModel() {
    var lng = ""
    var lat = ""
    var placeName = ""

    /**
     * 刷新未来天气和实时天气监控的LiveData
     */
    val data = MutableLiveData<Weather>()
    fun getdata(
        lng: String,
        lat: String,
        start: () -> Unit,
        error: () -> Unit,
        finally: () -> Unit
    ) {
        launchOnUITryCatch({
            start()
            val data1 = withContext(Dispatchers.IO) {
                RetrofitHelp.creat<WeatherApi>().getRealtimeWeather2(lng, lat)
            }
            val data2 = withContext(Dispatchers.IO) {
                RetrofitHelp.creat<WeatherApi>().getDailyWeather2(lng, lat)
            }
            val weather = Weather(data1.await().result.realtime, data2.await().result.daily)
            data.value = weather
        }, {
            error()
        }, {
            finally()
        }, false)
    }
}