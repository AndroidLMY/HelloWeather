package com.lmy.helloweather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmy.helloweather.R
import com.lmy.helloweather.databinding.ForecastItemBinding
import com.lmy.helloweather.model.WeatherList
import com.lmy.helloweather.model.getSky
import java.text.SimpleDateFormat
import java.util.*

/**
 * @功能:
 * @User Lmy
 * @Creat 2020/6/23 11:16
 * @Compony 永远相信美好的事情即将发生
 */
class WeatherAdapter(private val contex: Context, private val daily: WeatherList.Daily) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    private lateinit var binding: ForecastItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.forecast_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = daily.skycon.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sky = getSky(daily.skycon[position].value)
        val tempText =
            "${daily.temperature[position].min.toInt()} ~ ${daily.temperature[position].max.toInt()} ℃"
        binding.time = simpleDateFormat.format(daily.skycon[position].date)
        binding.weathericon = sky.icon
        binding.weatherstate = sky.info
        binding.temperature = tempText
        binding!!.executePendingBindings();
    }

    inner class ViewHolder(binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding!!.root)
}