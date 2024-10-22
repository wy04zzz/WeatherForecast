package com.gangan.weather.http.server;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestServer;

/**
 *    desc   : 正式环境
 */
public class ReleaseServer implements IRequestServer {

    private static String url="https://i.news.qq.com/";
    @NonNull
    @Override
    public String getHost() {
        return url;
    }

    public static String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}