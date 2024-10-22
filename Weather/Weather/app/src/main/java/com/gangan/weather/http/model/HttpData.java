package com.gangan.weather.http.model;

import com.google.gson.annotations.SerializedName;

import okhttp3.Headers;

/**
 *    desc   : 统一接口数据结构
 */
public class HttpData<T> {

    /** 请求头 */
    private Headers headers;

    /** 返回码 */
    private int code;
    private int status;
    /** 提示语 */
    @SerializedName("message")
    private String msg;
    /** 数据 */
    private T data;

    private String song_url;

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }


    public Headers getHeaders() {
        return headers;
    }

    public int getCode() {
        return code;
    }

    public String getSong_url() {
        return song_url;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 是否请求成功
     */
    public boolean isRequestSuccess() {
        return code == 200||status==200;
    }

}