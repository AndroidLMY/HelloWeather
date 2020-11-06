package com.lmy.helloweather.model


/**
 * @author Lmy
 * @功能: 地址数据
 * @Creat 2020/5/21 9:58
 * @Compony 永远相信美好的事物即将发生
 */
data class Place(
    val formatted_address: String,
    val id: String,
    val location: Location,
    val name: String,
    val place_id: String
)
data class Location(
    val lng: String,
    val lat: String
)


