package com.lmy.helloweather.base

import com.lmy.helloweather.model.DataResponse
import com.lmy.helloweather.model.DataWeatherResponse
import com.lmy.helloweather.model.Result
import com.lmy.helloweather.utils.e
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.lang.Exception

/**
 * @author lmy
 * @功能: Repository层的顶层封装
 * @Creat 2020/11/6 10:37 AM
 * @Compony 465008238@qq.com
 */
open class BaseRepository {
    //retrofit call封装
    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            e.message?.e()
            Result.Error(IOException(errorMessage, e))
        }
    }

    //执行请求函数
    /**
     *  传入一个被DataResponse修饰的T类型  T类型上限是Any  返回一个Result对象
     */
    suspend fun <T : Any> executeResponse(response: DataResponse<T>): Result<T> {
        return coroutineScope {
            //处理code码
            if (response.code != 0) {
                Result.Error(
                    IOException(if (response.message.isNullOrEmpty()) "${response.code}" else response.message)
                )
            } else {
                Result.Success(response.data)
            }
        }
    }


    /**
     * 由于天气接口的Api不规范 需要这种处理 正常使用其实应该是上面那种
     *  传入一个被DataResponse修饰的T类型  T类型上限是Any  返回一个Result对象
     */
    suspend fun <T : Any> executeResponse(response: DataWeatherResponse<T>): Result<T> {
        return coroutineScope {
            //处理code码
            if (response.status != "ok") {
                Result.Error(
                    IOException("请求出错")
                )
            } else {
                Result.Success(response.places)
            }
        }
    }


}