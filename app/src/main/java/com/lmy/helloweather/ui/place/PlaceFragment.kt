package com.lmy.helloweather.ui.place

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lmy.helloweather.MainActivity
import com.lmy.helloweather.R
import com.lmy.helloweather.adapter.PlaceAdapter
import com.lmy.helloweather.base.BaseFragment
import com.lmy.helloweather.logic.model.PlaceResponse
import com.lmy.helloweather.ui.weather.WeaTherActivity
import com.lmy.helloweather.utils.e
import com.lmy.helloweather.utils.startActivitys
import kotlinx.android.synthetic.main.fragment_place.*
import kotlinx.coroutines.*

class PlaceFragment : BaseFragment<PlaceViewModle>() {
    override fun getLayoutResId(): Int = R.layout.fragment_place
    override fun providerVMClass() = PlaceViewModle::class.java
    private lateinit var adapters: PlaceAdapter
    override fun initObserve() {

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
                initNet()
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                mViewModel?.placeList?.clear()
                adapters.notifyDataSetChanged()
            }
        }

        mViewModel?.apply {
            data.observe(this@PlaceFragment, Observer {
                if (it.places != null) {
                    recyclerView.visibility = View.VISIBLE
                    bgImageView.visibility = View.GONE
                    mViewModel?.placeList?.clear()
                    mViewModel?.placeList?.addAll(it.places)
                    adapters.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    fun save(place: PlaceResponse.Place) {
        mViewModel?.savePlace(place)
    }
    override fun initNet() {
        mViewModel?.getData(searchPlaceEdit.text.toString(), {}, {}) {
        }
    }
    override fun showProgress() {
        super.showProgress()
    }
    override fun hideProgress() {
        super.hideProgress()
    }

    override fun finishCreateView(state: Bundle?) {
        GlobalScope.launch(Dispatchers.Main) {

            getNumber()
            "SSS执行完毕1".e()
            getNumber()
            "SSS执行完毕2".e()
            getNumber()
            "SSS执行完毕3".e()


        }
    }

    suspend fun getNumber() {
        withContext(Dispatchers.IO) {
            for (num in 1..100) {
                "AAA$num".e()
            }
        }
    }
}