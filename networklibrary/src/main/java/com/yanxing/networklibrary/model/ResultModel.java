package com.yanxing.networklibrary.model;

/**
 * 方便响应成功和提示统一处理
 * Created by 李双祥 on 2020/5/11
 */
public class ResultModel<T> {

    /**
     * 可能用的状态码1
     */
    protected String status;

    /**
     * 可能用的状态码2
     */
    protected String code;
    /**
     * 可能用的信息1
     */
    protected String message;
    /**
     * 可能用的信息2
     */
    protected String msg;

    protected T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
