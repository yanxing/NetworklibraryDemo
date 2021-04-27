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

    private lateinit var serviceAPI:ServiceAPI

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun afterInstanceView() {
        init()
        getWeather204("深圳")
        getWeather205("上海")
    }

    private fun init() {
        val header = ArrayMap<String, String>()
        header.put("app_id", "oikqyppvlokrnkpq")
        header.put("app_secret", "YlpnRkR2TjhNRS9EU0ZKenFVNmllZz09")
        RetrofitManage.getInstance().init("https://www.mxnzp.com/api/",header, true)
        serviceAPI=RetrofitManage.getInstance().retrofit.create(ServiceAPI::class.java)
    }

    /**
     * 2.0.4之前版本使用，json实体需要继承BaseModel，2.1.3以前版本在jcenter上（jcenter服务于2022/02/01停用），
     * mavenCentral上只有2.1.3版本,包名没变。
     */
    private fun getWeather204(cityName: String) {
        serviceAPI
            .getWeather1(cityName)
            .compose(Transformer<Weather1>().iOMainHasProgress(this, mFragmentManager))
            .subscribe(object : AbstractObserver<Weather1>(this,mFragmentManager) {
                override fun onCall(t: Weather1) {
                    t.data?.let {
                        result1.text = it.toString()
                    }
                }
            })
    }

    /**
     * 2.0.5之后版本，实体不需要继承ResultModel，为了不影响已经使用该库的项目，
     * 使用了新的SimpleAbstractObserver和ResultModel<T>
     */
    private fun getWeather205(cityName: String) {
        serviceAPI
            .getWeather(cityName)
            .compose(Transformer<ResultModel<Weather>>().iOMainNoProgress(this))
            .subscribe(object : SimpleAbstractObserver<Weather>(this) {
                override fun onCollect(data: Weather?) {
                    data?.apply { result.text = toString() }
                }
            })
    }
}
