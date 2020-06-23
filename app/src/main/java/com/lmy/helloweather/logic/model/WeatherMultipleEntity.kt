package com.lmy.helloweather.logic.model

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @功能:
 * @User Lmy
 * @Creat 2020/6/23 15:34
 * @Compony 永远相信美好的事情即将发生
 */
class WeatherMultipleEntity : MultiItemEntity {
    override var itemType: Int
    var spanSize: Int
    var content: String? = null

    constructor(itemType: Int, spanSize: Int, content: String?) {
        this.itemType = itemType
        this.spanSize = spanSize
        this.content = content
    }

    constructor(itemType: Int, spanSize: Int) {
        this.itemType = itemType
        this.spanSize = spanSize
    }

    companion object {
        const val TEXT = 1
        const val IMG = 2
        const val IMG_TEXT = 3

        //上面type值标记布局得类型
        const val TEXT_SPAN_SIZE = 3
        const val IMG_SPAN_SIZE = 1
        const val IMG_TEXT_SPAN_SIZE = 4
        const val IMG_TEXT_SPAN_SIZE_MIN = 2
        //SPAN_SIZE标记当前type布局所占得位置
    }
}