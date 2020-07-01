package com.lmy.helloweather.ui.weather

import android.Manifest
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lmy.helloweather.R
import com.lmy.helloweather.adapter.WeatherAdapter
import com.lmy.helloweather.base.BaseActivity
import com.lmy.helloweather.databinding.ActivityWeaTherBinding
import com.lmy.helloweather.logic.model.Weather
import com.lmy.helloweather.logic.model.getSky
import com.lmy.helloweather.utils.bar_utils.StatusBarUtil
import com.lmy.helloweather.utils.e
import com.lmy.helloweather.utils.toast
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_wea_ther.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.now.*

class WeaTherActivity : BaseActivity<ActivityWeaTherBinding, WeatherViewModle>() {
    override fun providerVMClass() = WeatherViewModle::class.java
    override fun layoutId(): Int = R.layout.activity_wea_ther

    override fun initView() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        requestPermissionsX()
        navBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)//设置刷新颜色
        swipeRefresh.setOnRefreshListener {
            initNet()
        }
    }

    private fun requestPermissionsX() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA
            )
            .explainReasonBeforeRequest()//这个是请求之前提示用户的信息

            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白", "取消")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    "所有申请的权限都已通过".toast()
                } else {
                    "您拒绝了如下权限: $deniedList".toast()
                }
            }
    }

    fun setmodleData(lng: String, lat: String, name: String) {
        mViewModel!!.lng = lng
        mViewModel!!.lat = lat
        mViewModel!!.placeName = name
    }

    /**
     * 网络请求
     */
    override fun initNet() {
        mViewModel?.getdata(mViewModel!!.lng, mViewModel!!.lat, {
            "start net".e()
            showProgress()
            swipeRefresh.isRefreshing = true
        }, {
            "error net".e()
            swipeRefresh.isRefreshing = false
            hideProgress()
        }
        ) {
            swipeRefresh.isRefreshing = false
            "end net".e()
            hideProgress()
        }
    }

    override fun initObserve() {
        setmodleData(
            intent.getStringExtra("lng"),
            intent.getStringExtra("lat"),
            intent.getStringExtra("placeName")
        )

        mViewModel?.apply {
            data.observe(this@WeaTherActivity, Observer {
                if (it != null) {
                    showRealTime(it)
                } else {
                    "无法获取天气信息".toast()
                }
            })
        }
    }
    /**
     * 把实时天气的数据设置到界面上
     */
    private fun showRealTime(it: Weather) {
        binding.weather = it
        binding.cityname = mViewModel?.placeName
        binding.sky = getSky(it.weatherRealtime.skycon).info
        binding.nowbg = getSky(it.weatherRealtime.skycon).bg
        if (this::weatheradapter.isInitialized) {
            weatheradapter.notifyDataSetChanged()
        } else {
            weatheradapter = WeatherAdapter(this, it.weatherList)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@WeaTherActivity)
                adapter = weatheradapter
            }
        }
        weatherLayout.visibility = View.VISIBLE
    }



    override fun onDestroy() {
        super.onDestroy()
    }

    private lateinit var weatheradapter: WeatherAdapter



}
