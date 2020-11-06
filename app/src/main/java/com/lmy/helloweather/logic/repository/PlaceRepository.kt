package com.lmy.helloweather.logic.repository

import com.lmy.helloweather.base.BaseRepository
import com.lmy.helloweather.model.Result
import com.lmy.helloweather.logic.network.RetrofitHelp
import com.lmy.helloweather.logic.network.api.PlaceApi
import com.lmy.helloweather.model.Place

/**
 * @功能:
 * 这里把Repository请求接口和ViewMolde获取数据进行分离
 * 这样的好处是当viewMolde中返回得数据需要两个接口合并的时候
 * 只需要单独写个Repository方法进行处理即可
 * @User Lmy
 * @Creat 2020/7/30 10:32
 * @Compony 永远相信美好的事情即将发生
 */
object PlaceRepository : BaseRepository() {
    /**
     *ViewModel层调用获取地址信息数据方法
     */
    suspend fun getSerchPlace(name: String): Result<List<Place>> {
        return safeApiCall(
            call = { request_getSerchPlace(name) },
            errorMessage = "获取天气信息失败"
        )
    }

    /**
     * 请求调用地址信息接口数据并返回Result对象
     */
    suspend fun request_getSerchPlace(name: String): Result<List<Place>> {
        val placeResponse = RetrofitHelp.creat<PlaceApi>().serchPlace(name)
        return executeResponse(placeResponse)
    }
}