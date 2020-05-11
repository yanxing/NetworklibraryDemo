package com.yanxing.demo


/**
 * 天气
 * @author 李双祥 on 2020/5/9.
 */
/*        {
        "code": 1,
        "msg": "数据返回成功",
        "data": {
        "address": "广东省 深圳市",
        "cityCode": "440300",
        "temp": "18℃",
        "weather": "小雨",
        "windDirection": "东北",
        "windPower": "≤3级",
        "humidity": "92%",
        "reportTime": "2018-11-27 22:40:53"
    }
    }*/
data class Weather(
    var address: String, var cityCode: String, var temp: String, var weather: String
    , var windDirection: String, var windPower: String, var humidity: String, var reportTime: String
) {

    override fun toString(): String {
        return "Weather(address='$address', cityCode='$cityCode', temp='$temp', weather='$weather', windDirection='$windDirection', windPower='$windPower', humidity='$humidity', reportTime='$reportTime')"
    }
}