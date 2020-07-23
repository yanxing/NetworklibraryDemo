package com.yanxing.networklibrary;



import com.yanxing.networklibrary.intercepter.ParameterInterceptor;
import com.yanxing.networklibrary.intercepter.Interceptor;
import com.yanxing.networklibrary.util.GsonUtil;
import com.yanxing.networklibrary.util.LogUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lishuangxiang on 2017/04/01.
 */
public class RetrofitManage {

    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpClientBuilder;

    private RetrofitManage() {
    }

    public static RetrofitManage getInstance() {
        return SingletonHolder.RETROFIT_MANAGE;
    }

    private static class SingletonHolder {
        private static final RetrofitManage RETROFIT_MANAGE = new RetrofitManage();
    }

    /**
     * 初始化Retrofit
     *
     * @param baseUrl
     * @param log     true打印请求参数和返回数据
     */
    public synchronized void init(String baseUrl, boolean log) {
        mOkHttpClientBuilder = getOkHttpClientBuilderTimeout()
                .addInterceptor(new ParameterInterceptor());
        init(baseUrl, mOkHttpClientBuilder, log);
    }

    public synchronized void init(String baseUrl, OkHttpClient.Builder okHttpClientBuilder, boolean log) {
        LogUtil.isDebug = log;
        mOkHttpClientBuilder = okHttpClientBuilder;
        mRetrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClientBuilder.build());
    }

    public synchronized void init(String baseUrl, Map<String, String> headers, boolean log) {
        mOkHttpClientBuilder = getOkHttpClientBuilderTimeout()
                .addInterceptor(new ParameterInterceptor(headers));
        init(baseUrl, mOkHttpClientBuilder, log);
    }

    /**
     * 添加拦截器
     */
    public void setInterceptor(Interceptor interceptor) {
        mRetrofitBuilder.client(mOkHttpClientBuilder.addInterceptor(interceptor).build());
    }

    /**
     * 设置header，因为header在拦截器中添加，会重新创建默认OkHttpClientBuilder，之前设置的OkHttpClientBuilder属性将无效。
     * 若header是固定的（例如AppID，非登录后返回的token），可以在init方法中初始化headers
     *
     * @param headers
     */
    public void setHeader(Map<String, String> headers) {
        mOkHttpClientBuilder = getOkHttpClientBuilderTimeout()
                .addInterceptor(new ParameterInterceptor(headers));
        mRetrofitBuilder.client(mOkHttpClientBuilder.build());
    }

    /**
     * 设置OkHttpClient.Builder
     *
     * @param okHttpClientBuilder
     */
    public void setOkHttpClientBuilder(OkHttpClient.Builder okHttpClientBuilder) {
        mOkHttpClientBuilder = okHttpClientBuilder;
        mRetrofitBuilder.client(this.mOkHttpClientBuilder.build());
    }

    /**
     * 获取OkHttpClient.Builder，用于增加OkHttpClient.Builder属性设置
     *
     * @return
     */
    public OkHttpClient.Builder getOkHttpClientBuilder() {
        return mOkHttpClientBuilder;
    }

    /**
     * 设置超时时间，单位秒
     *
     * @param readTimeOut
     * @param connectTimeout
     * @param writeTimeout
     */
    public void setTimeOut(long readTimeOut, long connectTimeout, long writeTimeout) {
        mOkHttpClientBuilder
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS);
        mRetrofitBuilder.client(mOkHttpClientBuilder.build());
    }

    public Retrofit getRetrofit() {
        if (mRetrofitBuilder == null) {
            throw new NullPointerException("you need to call init method");
        }
        return mRetrofitBuilder.build();
    }

    private OkHttpClient.Builder getOkHttpClientBuilderTimeout() {
        return new OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS);
    }

}
