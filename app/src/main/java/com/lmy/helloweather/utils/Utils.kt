package com.lmy.helloweather.utils

import android.app.Dialog
import android.content.Intent
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.lmy.helloweather.WeatherApplication
import java.util.regex.Pattern

/**
 * @author Lmy
 * @功能: 顶层扩展函数
 * @Creat 2020/5/21 13:00
 * @Compony 永远相信美好的事物即将发生
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


/**
 *返回一组Int数据中最大得值
 *
 */
fun max(vararg nums: Int): Int {
    var maxNum = nums[0]
    for (num in nums) {
        maxNum = kotlin.math.max(maxNum, num)
    }
    return maxNum
}

/**
 * 通过泛型进行封装
 * java规定所有类型的数字都是可比较的  需要实现Comparabla接口
 *
 */
fun <T : Comparable<T>> max(vararg nums: T): T {
    if (nums.isEmpty()) throw RuntimeException("parms can not be empty")
    var maxNum = nums[0]
    for (num in nums) {
        if (num > maxNum) maxNum = num
    }
    return maxNum
}
/****************************************Activity跳转的扩展函数****************************************************/

/**
 * Activity跳转
 * startActivitys<T>()
 * 不携带参数的封装
 */
inline fun <reified T> startActivitys() {
    val intent = Intent(WeatherApplication.context, T::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    WeatherApplication.context.startActivity(intent)
}

/**
 * Activity跳转
 * 使用方法
 *   startActivitys<T> {
 *      putExtra("lng", place.location.lng)
 *   }
 * 携带参数的封装
 */
inline fun <reified T> startActivitys(block: Intent.() -> Unit) {
    val intent = Intent(WeatherApplication.context, T::class.java)
    intent.block()
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    WeatherApplication.context.startActivity(intent)
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
