package com.lmy.helloweather.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lmy.helloweather.R
import com.lmy.helloweather.WeatherApplication
import com.lmy.helloweather.customview.GrayFrameLayout
import com.lmy.helloweather.utils.bar_utils.StatusBarUtil
import com.lmy.helloweather.customview.dialog.LoadingDialog
import com.lmy.helloweather.databinding.ActivityWeaTherBinding

/**
 * @功能:
 * @Creat 2020/6/5 13:27
 * @User Lmy
 * @Compony JinAnChang
 */
abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected var mContext: Activity? = null
    protected var mViewModel: VM? = null
    protected lateinit var binding: VDB

    //加载类型是否是上拉加载
    protected var isLoading = false

    //分页的页码
    protected var pageNumber = 1

    //分页一页大小
    protected var pageSize = 20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId())
        mContext = this
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
        prepareBeforeInitView()
        initView()
        initVM()
        initObserve()
        initNet()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        /*为所有app界面添加灰度主题*/
        if (WeatherApplication.isGrayTheme) if ("FrameLayout".equals(name)) {
            val count = attrs.getAttributeCount();
            for (i in 0..count) {
                val attributeName = attrs.getAttributeName(i);
                val attributeValue = attrs.getAttributeValue(i);
                if (attributeName.equals("id")) {
                    val id = Integer.parseInt(attributeValue.substring(1));
                    val idVal = getResources().getResourceName(id);
                    if ("android:id/content".equals(idVal)) {
                        val grayFrameLayout =
                            GrayFrameLayout(
                                context,
                                attrs
                            );
                        return grayFrameLayout;
                    }
                }
            }
        }
        return super.onCreateView(name, context, attrs)
    }


    /**
     * 布局文件id
     */
    abstract fun layoutId(): Int

    /**
     * 初始化布局前的准备
     */
    open fun prepareBeforeInitView() {}

    /**
     *初始化view
     */
    open fun initView() {}

    /**
     * 初始化网络数据
     */
    open fun initNet() {}

    /**
     * 初始化订阅监听
     */
    open fun initObserve() {}

    /**
     *初始化ViewModle
     */
    private fun initVM() {
        providerVMClass()?.let { it ->
            mViewModel = ViewModelProvider(this).get(it)
            lifecycle.addObserver(mViewModel!!)
        }
    }


    /**
     * [BaseViewModel]的实现类
     */
    open fun providerVMClass(): Class<VM>? = null


    override fun onDestroy() {
        mViewModel?.let {
            lifecycle.removeObserver(it)
//            it.onDestory()
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