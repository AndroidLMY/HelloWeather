package com.lmy.helloweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.lmy.helloweather.WeatherApplication
import com.lmy.helloweather.model.Place

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 17:30
 * @Compony 永远相信美好的事物即将发生
 */
object PlaceDao {
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        WeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

}