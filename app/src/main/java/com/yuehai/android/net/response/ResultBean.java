package com.yuehai.android.net.response;

/**
 * Created by zhaoyuehai 2019/3/28
 */
public class ResultBean<T> {
    //    {"code":"10000","message":"success","serviceCode":"1.0","data":[
    private String code;
    private String message;
    private String serviceCode;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code.equals("10000");
    }
}
