package com.yanxing.demo

import android.util.ArrayMap
import com.yanxing.baselibrary.BaseActivity
import com.yanxing.networklibrary.AbstractObserver
import com.yanxing.networklibrary.RetrofitManage
import com.yanxing.networklibrary.SimpleAbstractObserver
import com.yanxing.networklibrary.Transformer
import com.yanxing.networklibrary.model.BaseModel
import com.yanxing.networklibrary.model.ResultModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun afterInstanceView() {
        init()
        getWeather1("深圳")
        getWeather("上海")
    }

    private fun init() {
        RetrofitManage.getInstance().init("https://www.mxnzp.com/api/", true)
        val header = ArrayMap<String, String>()
        header.put("app_id", "oikqyppvlokrnkpq")
        header.put("app_secret", "YlpnRkR2TjhNRS9EU0ZKenFVNmllZz09")
        RetrofitManage.getInstance().setHeader(header)
    }

    private fun getWeather1(cityName: String) {
        //需要继承BaseModel方式
        RetrofitManage.getInstance().retrofit.create(ServiceAPI::class.java)
            .getWeather1(cityName)
            .compose(Transformer<Weather1>().iOMainNoProgress(this))
            .subscribe(object : AbstractObserver<Weather1>(this) {
                override fun onCall(t: Weather1) {
                    t.data?.let {
                        result1.text = it.toString()
                    }
                }
            })
    }

    private fun getWeather(cityName: String) {
        //不需要继承ResultModel，ResultModel<T>泛型形式
        RetrofitManage.getInstance().retrofit.create(ServiceAPI::class.java)
            .getWeather(cityName)
            .compose(Transformer<ResultModel<Weather>>().iOMainNoProgress(this))
            //为了不影响已经使用该库的项目，使用了新的AbstractObserver和ResultModel
            .subscribe(object : SimpleAbstractObserver<ResultModel<Weather>>(this) {
                override fun onCall(t: ResultModel<Weather>) {
                    t.data?.let {
                        result.text = it.toString()
                    }
                }
            })
    }
}
