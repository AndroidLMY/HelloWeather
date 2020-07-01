package com.lmy.helloweather.adapter

import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lmy.helloweather.R
import com.lmy.helloweather.logic.model.WeatherMultipleEntity
import com.lmy.helloweather.utils.toast

/**
 * @功能:
 * @User Lmy
 * @Creat 2020/6/23 16:19
 * @Compony 永远相信美好的事情即将发生
 */
class MultipleItemQuickAdapter :
    BaseMultiItemQuickAdapter<WeatherMultipleEntity, BaseViewHolder>, LoadMoreModule,
    OnItemClickListener, OnItemChildClickListener {
    constructor(data: List<WeatherMultipleEntity?>?) : super(data as MutableList<WeatherMultipleEntity>?) {
        addItemType(WeatherMultipleEntity.TEXT, R.layout.item_text_view)
        addItemType(WeatherMultipleEntity.IMG, R.layout.item_image_view)
        addItemType(WeatherMultipleEntity.IMG_TEXT, R.layout.item_img_text_view)
    }

    constructor() {}

    override fun convert(holder: BaseViewHolder, item: WeatherMultipleEntity) {
        when (holder.itemViewType) {
            WeatherMultipleEntity.TEXT -> {
                holder.setText(R.id.tv, item.content)
            }
            WeatherMultipleEntity.IMG -> {
//                holder.setImageResource(R.id.iv, R.mipmap.animation_img1)
            }
            WeatherMultipleEntity.IMG_TEXT -> {
                when (holder.layoutPosition % 2) {
                    0 -> holder.setImageResource(R.id.iv, R.mipmap.animation_img1)
                    1 -> holder.setImageResource(R.id.iv, R.mipmap.animation_img2)
                    else -> {
                    }
                }
            }
            else -> {
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        "childView click".toast()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        "嵌套RecycleView item 收到: 点击了第 $position 一次".toast()
    }
}