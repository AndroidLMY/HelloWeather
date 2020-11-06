package com.lmy.helloweather.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lmy.helloweather.R
import com.lmy.helloweather.base.BaseActivity
import com.lmy.helloweather.utils.initViewModel
import com.lmy.helloweather.viewmodle.MainViewModle

class MainActivity : BaseActivity<MainViewModle>() {
    override val isBinding: Boolean
        get() = false//禁止使用DataBinding

    override fun initVM(): MainViewModle = initViewModel(this)
    override fun initObserve() {
    }

    override fun initView() {
    }

    override fun layoutId(): Int = R.layout.activity_main

}
