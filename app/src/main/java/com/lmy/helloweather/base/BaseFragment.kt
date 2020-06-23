package com.lmy.helloweather.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lmy.helloweather.customview.dialog.LoadingDialog

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    private var parentView: View? = null
    private var mContext: Context? = null
    protected var mViewModel: VM? = null

    //加载类型是否是上拉加载
    protected var isLoading = false

    //分页的页码
    protected var pageNumber = 1

    //分页一页大小
    protected var pageSize = 20
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentView = inflater.inflate(getLayoutResId(), container, false)
        return parentView
    }

    abstract fun getLayoutResId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finishCreateView(savedInstanceState)
        initVM()
        initObserve()
        initNet()
    }

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDestroy() {
        mViewModel?.let {
            lifecycle.removeObserver(it)
//            it.onDestory()
        }
        super.onDestroy()
        mContext = null
    }

    /**
     * 初始化views
     *
     * @param state
     */
    abstract fun finishCreateView(state: Bundle?)
    open fun initNet() {}
    open fun initObserve() {}

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