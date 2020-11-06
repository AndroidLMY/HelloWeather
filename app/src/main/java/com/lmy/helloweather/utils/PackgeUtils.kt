package com.lmy.helloweather.utils

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lmy.helloweather.WeatherApplication

/**
 * @author
 * @功能: 封装的方法
 * @Creat 2020/11/6 5:59 PM
 * @Compony 465008238@qq.com
 */


/**
 * 通过泛型进行封装
 * java规定所有类型的数字都是可比较的  需要实现Comparabla接口
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
 *   reified 这个是为了满足inline特性而设计的语法糖，因为给函数使用内联之后，
 *   编译器会用其函数体来替换掉函数调用，而如果该函数里面有泛型就可能会出现编译器不懂该泛型的问题，
 *   所以引入reified，使该泛型被智能替换成对应的类型
 * 携带参数的封装
 */
//
inline fun <reified T> startActivitys(block: Intent.() -> Unit) {
    val intent = Intent(WeatherApplication.context, T::class.java)
    intent.block()
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    WeatherApplication.context.startActivity(intent)
}

/**
 *初始化ViewModel的封装
 */
inline fun <reified T : ViewModel> initViewModel(context: ViewModelStoreOwner, noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null): T {
    val factory: ViewModelProvider.Factory? = factoryProducer?.invoke()
    return if (factory == null) {
        ViewModelProvider(context)[T::class.java]
    } else {
        ViewModelProvider(context, factory)[T::class.java]
    }
}


