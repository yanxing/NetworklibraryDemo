package com.yanxing.demo

import android.util.ArrayMap
import com.yanxing.baselibrary.BaseActivity
import com.yanxing.networklibrary.AbstractObserver
import com.yanxing.networklibrary.RetrofitManage
import com.yanxing.networklibrary.Transformer
import com.yanxing.networklibrary.model.BaseModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun afterInstanceView() {
        init()
        getWeather("上海")
    }

    private fun init() {
        RetrofitManage.getInstance().init("https://www.mxnzp.com/api/", true)
        val header = ArrayMap<String, String>()
        header.put("app_id", "oikqyppvlokrnkpq")
        header.put("app_secret", "YlpnRkR2TjhNRS9EU0ZKenFVNmllZz09")
        RetrofitManage.getInstance().setHeader(header)
    }

    private fun getWeather(cityName: String) {
        RetrofitManage.getInstance().retrofit.create(ServiceAPI::class.java)
            .getWeather(cityName)
            .compose(Transformer<BaseModel<Weather>>().iOMainNoProgress(this))
            .subscribe(object : AbstractObserver<BaseModel<Weather>>(this) {
                override fun onCall(t: BaseModel<Weather>) {
                    t.data?.let {
                        result.text = it.toString()
                    }
                }
            })
    }
}
