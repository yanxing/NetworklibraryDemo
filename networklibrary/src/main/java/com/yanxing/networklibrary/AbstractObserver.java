package com.yanxing.networklibrary;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;

import com.yanxing.networklibrary.model.BaseModel;
import com.yanxing.networklibrary.refresh.PullToRefresh;
import com.yanxing.networklibrary.util.ErrorCodeUtil;
import com.yanxing.networklibrary.util.ToastUtil;


/**
 * 根据接口返回状态码预处理，如果onNext方法中接口请求返回成功状态逻辑处理不一样，可仿照此类重写BaseAbstractObserver
 * json实体需要继承BaseModel
 * @deprecated 使用 {@link SimpleAbstractObserver}
 * Created by 李双祥 on 2017/5/23.
 */
public abstract class AbstractObserver<T extends BaseModel> extends BaseAbstractObserver<T> {

    protected AbstractObserver(Context context) {
        super(context);
    }

    protected AbstractObserver(Context context, String message) {
        super(context, message);
    }

    protected AbstractObserver(Context context, boolean isShowToast) {
        super(context, isShowToast);
    }

    protected AbstractObserver(Context context, PullToRefresh pullToRefresh) {
        super(context, pullToRefresh);
    }

    protected AbstractObserver(Context context, PullToRefresh pullToRefresh, String message) {
        super(context, pullToRefresh, message);
    }

    protected AbstractObserver(Context context, PullToRefresh pullToRefresh, FragmentManager fragmentManager, boolean isShowToast) {
        super(context, pullToRefresh, fragmentManager, isShowToast);
    }

    protected AbstractObserver(Context context, PullToRefresh pullToRefresh, boolean isShowToast) {
        super(context, pullToRefresh, isShowToast);
    }

    protected AbstractObserver(Context context, FragmentManager fragmentManager) {
        super(context, fragmentManager);
    }

    protected AbstractObserver(Context context, FragmentManager fragmentManager, String message) {
        super(context, fragmentManager, message);
    }

    protected AbstractObserver(Context context, FragmentManager fragmentManager, boolean isShowToast) {
        super(context, fragmentManager, isShowToast);
    }

    @Override
    public void onNext(T t) {
        if (ErrorCodeUtil.isSuccess(t.getStatus())||ErrorCodeUtil.isSuccess(t.getCode())) {
            onCall(t);
        } else {
            //业务逻辑不成功时，显示信息提示
            if (mContext != null && mIsShowToast) {
                if (TextUtils.isEmpty(mMessage)) {
                    //显示不成功时接口返回的信息提示（接口可能用的message/msg信息字段，如果都不匹配，则可重写onNext方法）
                    String msg=TextUtils.isEmpty(t.getMsg())?"":t.getMsg();
                    ToastUtil.showToast(mContext, TextUtils.isEmpty(t.getMessage()) ? msg : t.getMessage());
                } else {
                    //显示客户端自定义的信息提示
                    ToastUtil.showToast(mContext, mMessage);
                }
            }
        }
    }
}
