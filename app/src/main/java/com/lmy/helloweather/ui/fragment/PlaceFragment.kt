package com.lmy.helloweather.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lmy.helloweather.ui.activity.MainActivity
import com.lmy.helloweather.R
import com.lmy.helloweather.adapter.PlaceAdapter
import com.lmy.helloweather.base.BaseFragment
import com.lmy.helloweather.model.Place
import com.lmy.helloweather.ui.activity.WeaTherActivity
import com.lmy.helloweather.utils.e
import com.lmy.helloweather.utils.startActivitys
import com.lmy.helloweather.viewmodle.PlaceViewModle
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : BaseFragment<PlaceViewModle>(false) {
    override fun getLayoutResId(): Int = R.layout.fragment_place
    private lateinit var adapters: PlaceAdapter
    override fun initVM() = PlaceViewModle()
    override fun initViews() {
        if (activity is MainActivity && mViewModel?.isPlaceSaved()!!) {
            val place = mViewModel?.getSavedPlace()
            startActivitys<WeaTherActivity> {
                putExtra("lng", place!!.location.lng)
                putExtra("lat", place.location.lat)
                putExtra("placeName", place.name)
            }
            activity?.finish()
            return
        }
        adapters = PlaceAdapter(
            this,
            mViewModel!!.placeList
        )
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapters
        }
        //edittextvie的监听事件
        searchPlaceEdit.addTextChangedListener { editable ->
            if (editable.toString().isNotEmpty()) {
                initData()
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                mViewModel?.placeList?.clear()
                adapters.notifyDataSetChanged()
            }
        }
        isFirstRequest=false//设置首次进入界面不请求网络数据
    }

    override fun initData() {
        mViewModel?.getaddressData(searchPlaceEdit.text.toString())
    }

    override fun initObserve() {
        mViewModel?.apply {
            uiState.observe(this@PlaceFragment, Observer {
                if (it.showSuccess) {
                    if (it.placeResponse?.value != null) {
                        recyclerView.visibility = View.VISIBLE
                        bgImageView.visibility = View.GONE
                        mViewModel?.placeList?.clear()
                        mViewModel?.placeList?.addAll(it.placeResponse?.value!!)
                        adapters.notifyDataSetChanged()
                    }
                } else {
                    "未能查询到任何地点".e()
                }
            })
        }
    }

    fun save(place: Place) {
        mViewModel?.savePlace(place)
    }

    override fun finishCreateView(state: Bundle?) {
    }



}