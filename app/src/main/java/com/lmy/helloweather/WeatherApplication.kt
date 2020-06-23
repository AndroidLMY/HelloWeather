package com.lmy.helloweather

import android.app.Application
import android.content.Context
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lmy.helloweather.utils.LogUtils

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 9:53
 * @Compony 永远相信美好的事物即将发生
 */
class WeatherApplication : Application() {

    companion object {
        lateinit var context: Context
        var isGrayTheme = false
        const val TOKEN = "5u6uTQzJsFwR=xZW"
    }

    init {
        LogUtils.init("GetData", true)//初始化LogUtil工具类
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        LiveEventBus.config().supportBroadcast(this).lifecycleObserverAlwaysActive(true)
            .autoClear(false);
    }
}