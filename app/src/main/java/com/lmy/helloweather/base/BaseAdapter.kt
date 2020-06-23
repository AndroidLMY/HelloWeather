package com.lmy.helloweather.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @功能:
 * @User Lmy
 * @Creat 2020/6/23 13:39
 * @Compony 永远相信美好的事情即将发生
 */
abstract class BaseAdapter(var data: MutableList<Any> = mutableListOf()) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}


