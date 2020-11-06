package com.lmy.helloweather.logic.repository

import androidx.lifecycle.liveData
import com.lmy.helloweather.logic.dao.PlaceDao
import com.lmy.helloweather.model.Place
import com.lmy.helloweather.model.Weather
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

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
     * 记录选中城市
     */
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    /**
     * 获取选中的城市
     */
    fun getSavedPlace() = PlaceDao.getSavedPlace()

    /**
     * 判断是否有城市是被选中
     */
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()


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


