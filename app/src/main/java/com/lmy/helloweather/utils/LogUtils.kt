package com.lmy.helloweather.utils

import android.util.Log

/**
 * @功能: 全局控制log日志打印配置
 * @Creat 2019/07/15 16:18
 * @User Lmy
 * @By Android Studio
 */
object LogUtils {
    var isPrintLog = true //是否开启打印
    var TGA = "getdata"//log得TAG值

    fun init(tag: String, islog: Boolean) {
        TGA = tag
        isPrintLog = islog
    }
}
/****************************************Log打印得扩展函数****************************************************/
fun Any.d() {
    if (LogUtils.isPrintLog) {
        Log.d(LogUtils.TGA, "$this")
    }
}

fun Any.e() {
    if (LogUtils.isPrintLog) {
        Log.e(LogUtils.TGA, "$this")
    }
}

fun Any.i() {
    if (LogUtils.isPrintLog) {
        Log.i(LogUtils.TGA, "$this")
    }
}

fun Any.v() {
    if (LogUtils.isPrintLog) {
        Log.v(LogUtils.TGA, "$this")
    }
}

fun Any.w() {
    if (LogUtils.isPrintLog) {
        Log.w(LogUtils.TGA, "$this")
    }
}