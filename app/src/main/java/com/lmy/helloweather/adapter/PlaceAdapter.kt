package com.lmy.helloweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lmy.helloweather.R
import com.lmy.helloweather.model.Place
import com.lmy.helloweather.ui.fragment.PlaceFragment
import com.lmy.helloweather.ui.activity.WeaTherActivity
import com.lmy.helloweather.utils.startActivitys
import kotlinx.android.synthetic.main.activity_wea_ther.*

class PlaceAdapter(
    private val fragment: PlaceFragment,
    private val placeList: List<Place>
) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.placeName.setOnClickListener {
            /**
             * 这里加了一个判断如果activity属于WeaTherActivity则关闭测护栏
             */
            val place = placeList[holder.adapterPosition]
            val activity = fragment.activity
            fragment.save(place)
            if (activity is WeaTherActivity) {
                activity.drawerLayout.closeDrawers()
                activity.setmodleData(place.location.lng, place.location.lat, place.name)
                activity.initData()
            } else {
                startActivitys<WeaTherActivity> {
                    putExtra("lng", place.location.lng)
                    putExtra("lat", place.location.lat)
                    putExtra("placeName", place.name)
                }
                fragment.activity?.finish()
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.formatted_address
    }

    override fun getItemCount() = placeList.size

}

/**
//             * 使用这种方式不会造成线程阻塞 所以并会影响toast的有弹出
//             * CoroutineScope来气一个协程
//             */
//            val job = Job()
//            val coroutineScope = CoroutineScope(Job())
//            //下面两个coroutineScope launch其实是并发的
//            coroutineScope.launch {
//                LogUtil.e("coroutineScope111ss")
//                val placeResponse = HellWeatherNetWork.serchWeatherRealTime(
//                    place.location.lng.toString(),
//                    place.location.lat.toString()
//                )
//                LogUtil.e("coroutineScope111ee")
//            }
//            coroutineScope.launch {
//                LogUtil.e("coroutineScope222ss")
//                val placeResponse = HellWeatherNetWork.serchWeatherRealTime(
//                    place.location.lng.toString(),
//                    place.location.lat.toString()
//                )
//                LogUtil.e("coroutineScope222ee")
//            }
//            "运行".toast()



