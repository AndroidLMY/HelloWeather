package com.lmy.helloweather.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @功能:
 * @User Lmy
 * @Creat 2020/6/23 13:40
 * @Compony 永远相信美好的事情即将发生
 */
open class BaseViewHolder(var dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {}