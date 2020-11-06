package com.lmy.helloweather.base

import android.app.Activity
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lmy.helloweather.WeatherApplication
import com.lmy.helloweather.customview.GrayFrameLayout
import com.lmy.helloweather.utils.bar_utils.StatusBarUtil
import com.lmy.helloweather.customview.dialog.LoadingDialog
import com.lmy.helloweather.utils.setGaryTheme

/**
 * @功能:
 * @Creat 2020/6/5 13:27
 * @User Lmy
 * @Compony JinAnChang
 */
abstract class BaseActivity<VM : BaseViewModel>(isDataBinding: Boolean = true) :
    AppCompatActivity() {
    //是否要使用DataBinding
    protected open val isBinding = isDataBinding
    protected var mContext: Activity? = null
    protected lateinit var mViewModel: VM
    protected lateinit var binding: ViewDataBinding
    protected var isFirstRequest: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        mViewModel = initVM()
        lifecycle.addObserver(mViewModel!!)
        mContext = this
        initStyle();
        initView()
        initObserve()
        if (isFirstRequest) {
            initData()
        }
    }

    private fun initDataBinding() {
        if (isBinding) {
            val id: Int = layoutId();
            binding = DataBindingUtil.setContentView(this, layoutId())
        } else
            setContentView(layoutId())
    }

    /**
     * 初始化Activity的界面风格
     */
    private fun initStyle() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        /**
         * 设置状态栏透明StatusBarUtil.setTranslucentStatus(this);
         */
        StatusBarUtil.setTranslucentStatus(this)
//        StatusBarUtil.setStatusBarColor(this, resources.getColor(R.color.white))
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            /**
             * 如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清
             * 于是设置一个状态栏颜色为半透明,这样半透明+白=灰, 状态栏的文字能看得清
             */
            StatusBarUtil.setStatusBarColor(this, 0x55000000)
        }
    }

    /**
     * 重写onCreateView
     * 用于设置是否要增加全局灰度的主题
     */
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        //添加灰度主题
        if (WeatherApplication.isGrayTheme)
            setGaryTheme(window)
        return super.onCreateView(name, context, attrs)
    }


    /**
     * 布局文件id
     */
    abstract fun layoutId(): Int

    /**
     *初始化ViewModle
     */
    abstract fun initVM(): VM


    /**
     *初始化view
     */
    abstract fun initView()

    /**
     * 初始化订阅监听
     */
    abstract fun initObserve()

    /**
     * 初始化数据
     */
    open fun initData() {}

    override fun onDestroy() {
        mViewModel?.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
        mContext = null
    }

    private var loadingDialog: LoadingDialog? = null
    open fun showProgress() {
        mContext?.let {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(mContext!!).apply {
                    setCancelable(false)
                }
            }
            loadingDialog?.apply {
                show()
                showLoading()
            }
        }
    }

    open fun hideProgress() {
        mContext?.let {
            loadingDialog?.apply {
                cancel()
                hisLoading()
            }
        }
    }

}