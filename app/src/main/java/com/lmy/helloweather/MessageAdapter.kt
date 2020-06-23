package com.lmy.helloweather

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @功能:
 * @User Lmy
 * @Creat 2020/6/23 13:51
 * @Compony 永远相信美好的事情即将发生
 */
class MessageAdapter(
    cont: Context?,
    li: List<String>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<String>? = null
    private var context: Context? = null
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        var binding: ViewDataBinding? = null
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.fragment_place,
            viewGroup,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    internal inner class MyViewHolder(itemView: ViewDataBinding?) :
        RecyclerView.ViewHolder(itemView!!.root) {
        var binding: ViewDataBinding? = null

        init {
            binding = itemView
        }
    }

    init {
        list = li
        context = cont
    }
}