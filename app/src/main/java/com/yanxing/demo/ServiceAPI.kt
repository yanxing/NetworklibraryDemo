package com.yanxing.demo

import com.yanxing.networklibrary.model.BaseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author 李双祥 on 2020/5/9.
 */
interface ServiceAPI {

    /**
     * 获取天气
     */
    @GET("weather/current/{cityName}")
    fun getWeather(@Path("cityName") cityName: String): Observable<BaseModel<Weather>>

}