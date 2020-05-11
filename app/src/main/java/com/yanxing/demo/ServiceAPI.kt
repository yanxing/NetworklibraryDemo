package com.yanxing.demo

import com.yanxing.networklibrary.model.BaseModel
import com.yanxing.networklibrary.model.ResultModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author 李双祥 on 2020/5/9.
 */
interface ServiceAPI {

    /**
     * 获取天气,旧的方式
     */
    @GET("weather/current/{cityName}")
    fun getWeather1(@Path("cityName") cityName: String): Observable<Weather1>

    /**
     * 获取天气
     */
    @GET("weather/current/{cityName}")
    fun getWeather(@Path("cityName") cityName: String): Observable<ResultModel<Weather>>



}