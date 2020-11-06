package com.lmy.helloweather.utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lmy.helloweather.WeatherApplication
import com.lmy.helloweather.viewmodle.WeatherViewModle
import java.util.regex.Pattern

/**
 * @author Lmy
 * @功能: 顶层扩展函数
 * @Creat 2020/5/21 13:00
 * @Compony 永远相信美好的事物即将发生
 */






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
 * 设置全局灰度主题
 */
fun setGaryTheme(window: Window) {
    val paint = Paint()
    val cm = ColorMatrix()
    cm.setSaturation(0f)
    paint.colorFilter = ColorMatrixColorFilter(cm)
    window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
}


