package com.lmy.helloweather.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * @功能: 实现全局的灰度主题
 * @Creat 2020/4/8 15:38
 * @User Lmy
 * @Compony liming
 */

class GrayFrameLayout(context: Context?, attrs: AttributeSet?) : FrameLayout(context!!, attrs) {
    private val mPaint = Paint()
    override fun dispatchDraw(canvas: Canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.dispatchDraw(canvas)
        canvas.restore()
    }

    override fun draw(canvas: Canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.draw(canvas)
        canvas.restore()
    }

    init {
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        mPaint.colorFilter = ColorMatrixColorFilter(cm)
    }
}