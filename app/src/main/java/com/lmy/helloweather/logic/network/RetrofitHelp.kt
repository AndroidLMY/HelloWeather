package com.lmy.helloweather.logic.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Lmy
 * @功能: RetrofitHelp初始化的帮助类
 * @Creat 2020/5/21 10:03
 * @Compony 永远相信美好的事物即将发生
 */
object RetrofitHelp {
    private const val BASE_URL = "https://api.caiyunapp.com/"
    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor()
    }
    private val client by lazy {
//        okhttp设置部分，此处还可再设置网络参数
        OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    init {
        //初始化loggingInterceptor 日志拦截器  和okhttp3失败重连的配置；
        loggingInterceptor.setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * 这里使用泛型来创建ServiceClass
     */
    fun <T> creat(service: Class<T>): T = retrofit.create(service)

    /**
     * 这里使用了泛型实例化 主要的区别在于调用方式
     *  ServiceCreator.creat(PalceService::class.java)
     *  ServiceCreator.creat<PalceService>()
     */
    inline fun <reified T> creat(): T =
        creat(T::class.java)
}