# NetworklibraryDemo
对retrofit2+rxjava2网络请求的简单封装（包括网络请求时显示等待对话框和请求完成后上下拉刷新控件置为完成状态）。
* gradle 
```java
 compile 'com.yanxing:networklibrary:1.2.1'
 ```
 androidx
 ```java
 compile 'com.yanxing:networklibrary:2.0.4'
 ```
```kotlin
 //实体需要继承BaseModel
  RetrofitManage.getInstance().retrofit.create(ServiceAPI::class.java)
      .getWeather1(cityName)
      .compose(Transformer<Weather1>().iOMainNoProgress(this))
      .subscribe(object : AbstractObserver<Weather1>(this) {
           override fun onCall(t: Weather1) {
                 
             }
        })
```
其中使用了Rxlifecycle2来防止RxJava的内存泄露，所以基类需要继承Rxlifecycle2相关类，或者实现LifecycleProvider接口


### 后续
上面的做法需要实体类继承BaseModel，并且实体类需要包括一个静态DataBean类，不利于已经有很多实体类项目的接入，例如：
```json
{
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
}
```
生成的实体类为
```kotlin
data class Weather1(var data: DataBean? = null):BaseModel() {
    //Weather1实体需要继承BaseModel，静态类DataBean才是有用数据
    data class DataBean(
        var address: String,
        var cityCode: String,
        var temp: String,
        var weather: String,
        var windDirection: String,
        var windPower: String,
        var humidity: String,
        var reportTime: String
    )
}
```
新版本可以不用继承该库中的实体，新的实体为
```kotlin
data class Weather(
    var address: String, 
    var cityCode: String,
    var temp: String,
    var weather: String, 
    var windDirection: String,
    var windPower: String,
    var humidity: String,
    var reportTime: String
)
```
使用方式
```kotlin
 //实体不需要继承ResultModel，使用ResultModel<T>形式
  RetrofitManage.getInstance().retrofit.create(ServiceAPI::class.java)
      .getWeather(cityName)
      .compose(Transformer<ResultModel<Weather>>().iOMainNoProgress(this))
      //为了不影响已经使用该库的项目，使用了新的AbstractObserver和ResultModel
      .subscribe(object : SimpleAbstractObserver<ResultModel<Weather>>(this) {
           override fun onCall(t: ResultModel<Weather>) {
                   
            }
      })
```            
