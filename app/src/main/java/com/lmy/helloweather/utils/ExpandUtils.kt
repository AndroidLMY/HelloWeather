package com.lmy.helloweather.utils

import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.lmy.helloweather.WeatherApplication
import java.util.regex.Pattern

/**
 * @author
 * @功能: 扩展函数的工具类
 * @Creat 2020/11/6 5:55 PM
 * @Compony 465008238@qq.com
 */
/**
 * 吐司扩展函数
 */
fun Any.toast(gravity: Int = Gravity.BOTTOM) {
    Toast.makeText(WeatherApplication.context, this.toString(), Toast.LENGTH_LONG).apply {
        setGravity(gravity, 0, 0)
    }.show()
}

/**
 * 判断手机号是否合法
 */
fun String.isPhoneNumber(): Boolean {
    return if (this.isEmpty()) {
        false
    } else Pattern.matches(REGEX_MOBILE, this)
}

/**
 * 判断手机号是否合法
 */
fun Int.isPhoneNumber(): Boolean {
    return if (this.toString().isEmpty()) {
        false
    } else Pattern.matches(REGEX_MOBILE, this.toString())
}

//校验手机是否合规 2020年最全的国内手机号格式
private const val REGEX_MOBILE =
    "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))"

