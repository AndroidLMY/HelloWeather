package com.lmy.helloweather.ui.basequick

import androidx.recyclerview.widget.GridLayoutManager
import com.lmy.helloweather.R
import com.lmy.helloweather.adapter.MultipleItemQuickAdapter
import com.lmy.helloweather.adapter.animator.CustomAnimation2
import com.lmy.helloweather.base.BaseActivity
import com.lmy.helloweather.databinding.ActivityBaseQuickAdapterBinding
import com.lmy.helloweather.logic.model.WeatherMultipleEntity
import com.lmy.helloweather.utils.toast
import kotlinx.android.synthetic.main.activity_base_quick_adapter.*
import kotlinx.coroutines.*
import java.util.*

class BaseQuickAdapterActivity :
    BaseActivity<ActivityBaseQuickAdapterBinding, BaseQuickAdapterViewModle>() {
    override fun layoutId(): Int = R.layout.activity_base_quick_adapter
    lateinit var multipadapter: MultipleItemQuickAdapter
    lateinit var datalist: MutableList<WeatherMultipleEntity>
    override fun initView() {
        datalist = getMultipleItemData()
        if (this::datalist.isInitialized) {
            multipadapter = MultipleItemQuickAdapter(datalist)
            rv_basequick_view.apply {
                layoutManager = GridLayoutManager(this@BaseQuickAdapterActivity, 4)
                adapter = multipadapter
            }
            val headerView = layoutInflater.inflate(R.layout.head_view, rv_basequick_view, false)
            multipadapter.apply {
                addHeaderView(headerView.apply {
                    setOnClickListener {
                        "headerView点击事件".toast()
                    }
                })
                /*上拉加载界面*/
                loadMoreModule.setOnLoadMoreListener {
                    loadmore()
                }
                /*点击事件监听*/
                setOnItemClickListener { adapter, view, position ->
                    position.toast()
                }
                setOnItemChildClickListener { adapter, view, position ->
                    "Child$position".toast()
                }
                /*返回每个item所占用的位置*/
                setGridSpanSizeLookup { gridLayoutManager: GridLayoutManager?, viewType: Int, position: Int ->
                    datalist[position].spanSize
                }
                /*multipadapter.setAnimationWithDefault(AnimationType.ScaleIn)*/
                /*自定义动画*/
                adapterAnimation = CustomAnimation2()
                animationEnable = true
                /*设置空白页
                * 方式一：直接传入 layout id
                **/
                setEmptyView(R.layout.loading_view)

            }
        }
    }

    private fun loadmore() {
        /*模拟网络请求 延迟1妙刷新*/
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            multipadapter.loadMoreModule.isEnableLoadMore = true
            //不是第一页，则用add
            multipadapter.addData(getMultipleItemData())
            multipadapter.loadMoreModule.loadMoreComplete()
            async {
            }.await()
        }
    }

    override fun initNet() {
    }

    override fun initObserve() {
    }

    fun getMultipleItemData(): MutableList<WeatherMultipleEntity> {
        val list: MutableList<WeatherMultipleEntity> =
            ArrayList<WeatherMultipleEntity>()
        for (i in 0..4) {
            list.add(
                WeatherMultipleEntity(
                    WeatherMultipleEntity.IMG,
                    WeatherMultipleEntity.IMG_SPAN_SIZE
                )
            )
            list.add(
                WeatherMultipleEntity(
                    WeatherMultipleEntity.TEXT,
                    WeatherMultipleEntity.TEXT_SPAN_SIZE,
                    "CymChad"
                )
            )
            list.add(
                WeatherMultipleEntity(
                    WeatherMultipleEntity.IMG_TEXT,
                    WeatherMultipleEntity.IMG_TEXT_SPAN_SIZE
                )
            )
            list.add(
                WeatherMultipleEntity(
                    WeatherMultipleEntity.IMG_TEXT,
                    WeatherMultipleEntity.IMG_TEXT_SPAN_SIZE_MIN
                )
            )
            list.add(
                WeatherMultipleEntity(
                    WeatherMultipleEntity.IMG_TEXT,
                    WeatherMultipleEntity.IMG_TEXT_SPAN_SIZE_MIN
                )
            )
        }
        return list
    }


}