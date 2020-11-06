package com.lmy.helloweather.ui.activity

import android.Manifest
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lmy.helloweather.R
import com.lmy.helloweather.adapter.WeatherAdapter
import com.lmy.helloweather.base.BaseActivity
import com.lmy.helloweather.databinding.ActivityWeaTherBinding
import com.lmy.helloweather.model.Weather
import com.lmy.helloweather.model.getSky
import com.lmy.helloweather.utils.bar_utils.StatusBarUtil
import com.lmy.helloweather.utils.initViewModel
import com.lmy.helloweather.utils.toast
import com.lmy.helloweather.viewmodle.ViewModelFactory.WeatherViewModelFactory
import com.lmy.helloweather.viewmodle.WeatherViewModle
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_wea_ther.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.now.*

class WeaTherActivity : BaseActivity<WeatherViewModle>() {
    override fun layoutId(): Int = R.layout.activity_wea_ther
    override fun initVM(): WeatherViewModle = initViewModel(this) {
        //向ViewModel传递参数
        WeatherViewModelFactory(
            intent.getStringExtra("lng"),
            intent.getStringExtra("lat"),
            intent.getStringExtra("placeName")
        )
    }

    private lateinit var weatheradapter: WeatherAdapter
    override fun initView() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        requestPermissionsX()
        navBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)//设置刷新颜色
        swipeRefresh.setOnRefreshListener {
            mViewModel.getdata()
        }
    }

    override fun initObserve() {
        mViewModel.apply {
            uiState.observe(this@WeaTherActivity, Observer {
                swipeRefresh.isRefreshing = it.isRefresh
                if (it.showSuccess) {
                    showRealTime(it.weather?.value!!)
                } else {
                    it.showErrorText?.toast()
                }
            })
        }
    }

    /**
     * 网络请求
     */
    override fun initData() {
        mViewModel.getdata()
    }

    /**
     * 使用PermissionsX进行运行时权限的申请
     */
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

    /**
     * 修改ViewModele中存储的经纬度和城市信息
     *
     */
    fun setmodleData(lng: String, lat: String, name: String) {

    }

    /**
     * 把实时天气的数据设置到界面上
     */
    private fun showRealTime(it: Weather) {
        (binding as ActivityWeaTherBinding).apply {
            weather = it
            cityname = mViewModel.placeName
            sky = getSky(it.weatherRealtime.skycon).info
            nowbg = getSky(it.weatherRealtime.skycon).bg
        }
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

}
