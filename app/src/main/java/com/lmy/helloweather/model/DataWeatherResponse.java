package com.lmy.helloweather.model;
/**
 * @author lmy
 * @功能: 统一处理请求地址返回数据的封装
 * 这里的天气数据接口不规范没有标准 只是请求地址的时候调用类
 * @Creat 2020/11/6 3:57 PM
 * @Compony 465008238@qq.com
 */

public class DataWeatherResponse<T> {
    private String status;
    private T places;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public T getPlaces() {
        return places;
    }

    public void setPlaces(T places) {
        this.places = places;
    }
}

