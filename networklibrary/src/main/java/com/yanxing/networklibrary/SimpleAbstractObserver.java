package com.yanxing.networklibrary;

import android.content.Context;
import android.text.TextUtils;

import androidx.fragment.app.FragmentManager;

import com.yanxing.networklibrary.model.ResultModel;
import com.yanxing.networklibrary.refresh.PullToRefresh;
import com.yanxing.networklibrary.util.ErrorCodeUtil;
import com.yanxing.networklibrary.util.ToastUtil;


/**
 * 在onNext方法中预处理，如果接口返回的json格式字段和ResultModel不一样，可仿照此类重写BaseAbstractObserver的onNext方法
 * json实体不需要继承ResultModel
 * Created by 李双祥 on 2020/5/11.
 */
public abstract class SimpleAbstractObserver<T> extends BaseAbstractObserver<ResultModel<T>> {

    protected SimpleAbstractObserver(Context context) {
        super(context);
    }

    protected SimpleAbstractObserver(Context context, String message) {
        super(context, message);
    }

    protected SimpleAbstractObserver(Context context, boolean isShowToast) {
        super(context, isShowToast);
    }

    protected SimpleAbstractObserver(Context context, PullToRefresh pullToRefresh) {
        super(context, pullToRefresh);
    }

    protected SimpleAbstractObserver(Context context, PullToRefresh pullToRefresh, String message) {
        super(context, pullToRefresh, message);
    }

    protected SimpleAbstractObserver(Context context, PullToRefresh pullToRefresh, FragmentManager fragmentManager, boolean isShowToast) {
        super(context, pullToRefresh, fragmentManager, isShowToast);
    }

    protected SimpleAbstractObserver(Context context, PullToRefresh pullToRefresh, boolean isShowToast) {
        super(context, pullToRefresh, isShowToast);
    }

    protected SimpleAbstractObserver(Context context, FragmentManager fragmentManager) {
        super(context, fragmentManager);
    }

    protected SimpleAbstractObserver(Context context, FragmentManager fragmentManager, String message) {
        super(context, fragmentManager, message);
    }

    protected SimpleAbstractObserver(Context context, FragmentManager fragmentManager, boolean isShowToast) {
        super(context, fragmentManager, isShowToast);
    }

    @Override
    public void onNext(ResultModel<T> resultModel) {
        if (ErrorCodeUtil.isSuccess(resultModel.getStatus()) || ErrorCodeUtil.isSuccess(resultModel.getCode())) {
            onCall(resultModel);
            onCollect(resultModel.getData());
        } else {
            //业务逻辑不成功时，显示信息提示
            if (mContext != null && mIsShowToast) {
                if (TextUtils.isEmpty(mMessage)) {
                    //显示不成功时接口返回的信息提示（接口可能用的message/msg信息字段，如果都不匹配，则可重写onNext方法）
                    String msg = TextUtils.isEmpty(resultModel.getMsg()) ? "" : resultModel.getMsg();
                    ToastUtil.showToast(mContext, TextUtils.isEmpty(resultModel.getMessage()) ? msg : resultModel.getMessage());
                } else {
                    //显示客户端自定义的信息提示
                    ToastUtil.showToast(mContext, mMessage);
                }
            }
        }
    }

    @Override
    public void onCall(ResultModel<T> resultModel) {
    }

    /**
     * 新，请求成功返回data中的数据，不同于onCall(T t)方法
     *
     * @param data
     */
    public abstract void onCollect(T data);

}
