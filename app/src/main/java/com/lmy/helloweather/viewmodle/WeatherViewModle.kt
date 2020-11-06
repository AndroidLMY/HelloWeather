package com.lmy.helloweather.viewmodle

import androidx.lifecycle.*
import com.lmy.helloweather.base.BaseViewModel
import com.lmy.helloweather.model.Weather
import com.lmy.helloweather.logic.repository.WeatherRepository
import kotlinx.coroutines.launch

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 16:01
 * @Compony 永远相信美好的事物即将发生
 */
class WeatherViewModle(lon: String, lat: String, placname: String) : BaseViewModel() {
    var lng = lon
    var lat = lat
    var placeName = placname
    private val _uiState = MutableLiveData<WeatherModel>()
    val uiState: LiveData<WeatherModel>
        get() = _uiState

    fun getdata() {
        viewModelScope.launch {
            val data = MutableLiveData<Weather>()
            updateUiState(isRefresh = true)
            var weather = WeatherRepository.getWeatherAll(lng, lat)
            checkResult(weather, {
                data.value = it
                updateUiState(isRefresh = false, weather = data, showSuccess = true)
            }, {
                updateUiState(showErrorText = it, isRefresh = false)
            })
        }
    }

    private fun updateUiState(
        showSuccess: Boolean = false,
        isRefresh: Boolean = false,
        isShowProgress: Boolean = false,
        showErrorText: String? = null,
        weather: MutableLiveData<Weather>? = null
    ) {
        val uiModel = WeatherModel(showSuccess, isRefresh, isShowProgress, showErrorText, weather)
        _uiState.value = uiModel
    }
}

data class WeatherModel(
    val showSuccess: Boolean,
    val isRefresh: Boolean,
    val isShowProgress: Boolean,
    var showErrorText: String?,
    val weather: MutableLiveData<Weather>?
)
