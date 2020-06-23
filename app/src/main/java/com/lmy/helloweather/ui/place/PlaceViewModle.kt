package com.lmy.helloweather.ui.place

import android.app.DownloadManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.lmy.helloweather.base.BaseViewModel
import com.lmy.helloweather.logic.Repository
import com.lmy.helloweather.logic.model.PlaceResponse
import com.lmy.helloweather.logic.model.Weather
import com.lmy.helloweather.logic.network.api.PlaceApi
import com.lmy.helloweather.logic.network.api.WeatherApi
import com.lmy.helloweather.logic.network.base.RetrofitHelp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 10:41
 * @Compony 永远相信美好的事物即将发生
 */
class PlaceViewModle : BaseViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<PlaceResponse.Place>()
    val data = MutableLiveData<PlaceResponse>()
    fun getData(name: String, start: () -> Unit, error: () -> Unit, finally: () -> Unit) {
        launchOnUITryCatch({
            start()
            val data1 = withContext(Dispatchers.IO) {
                RetrofitHelp.creat<PlaceApi>().serchPlace2(name)
            }
            data.value = data1.await()
        }, {
            error()
        }, {
            finally()
        }, false)
    }

    fun savePlace(place: PlaceResponse.Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()
}