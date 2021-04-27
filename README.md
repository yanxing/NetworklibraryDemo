# NetworklibraryDemo
对retrofit2+rxjava2网络请求的简单封装。
* 适用于接口返回json数据的项目
* 使用了Rxlifecycle2来防止RxJava的内存泄露，所以基类需要继承Rxlifecycle2相关类，或者实现LifecycleProvider接口
* 可以设置接口请求时等待对话框
* 可以设置接口请求完成后上下拉刷新控件置为完成状态，刷新组件需要实现PullToRefresh接口。
* 接口请求参数和返回数据打印。
* 可以设置自定义的拦截器、请求头参数和okHttpClient。

gradle接入，mavenCentral
 ```java
 implementation 'io.github.yanxing:networklibrary:2.1.3'
 ```

请求示例
```kotlin
  serviceAPI
      .getWeather(cityName)
      .compose(Transformer<ResultModel<Weather>>().iOMainNoProgress(this))
      .subscribe(object : SimpleAbstractObserver<Weather>(this) {
           override fun onCollect(data: Weather?) {
                   
            }
      })
```            
[retrofit2+kotlin协程重写networklibrary](https://github.com/yanxing/NetworklibraryKtDemo)
