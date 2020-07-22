package com.yanxing.networklibrary.model;

/**
 * 方便响应成功和提示统一处理
 * Created by 李双祥 on 2017/5/23
 * @deprecated 使用 {@link ResultModel}
 */
public class BaseModel{

    /**
     * 返回状态码1
     */
    protected String status;

    /**
     * 返回状态码2
     */
    protected String code;
    /**
     * 返回信息1
     */
    protected String message;
    /**
     * 返回信息2
     */
    protected String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
