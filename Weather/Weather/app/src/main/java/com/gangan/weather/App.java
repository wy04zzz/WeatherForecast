package com.gangan.weather;

import android.app.Application;

import androidx.annotation.NonNull;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.gangan.weather.http.model.RequestHandler;
import com.gangan.weather.http.server.ReleaseServer;
import com.hjq.http.EasyConfig;
import com.hjq.http.config.IRequestInterceptor;
import com.hjq.http.model.HttpHeaders;
import com.hjq.http.model.HttpParams;
import com.hjq.http.request.HttpRequest;
import com.kongzue.dialogx.DialogX;


import org.litepal.LitePal;

import okhttp3.OkHttpClient;


public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {    	     
        super.onCreate();
        instance = this;
        DialogX.init(this);
        //初始化数据库
        LitePal.initialize(this);
        ReleaseServer server = new ReleaseServer();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        EasyConfig.with(okHttpClient)
                // 是否打印日志
                .setLogEnabled(true)
                // 设置服务器配置（必须设置）
                .setServer(server)
                // 设置请求处理策略（必须设置）
                .setHandler(new RequestHandler(this))
                // 设置请求参数拦截器
                .setInterceptor(new IRequestInterceptor() {
                    @Override
                    public void interceptArguments(@NonNull HttpRequest<?> httpRequest,
                                                   @NonNull HttpParams params,
                                                   @NonNull HttpHeaders headers) {
                        headers.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    }
                })
                // 设置请求重试次数
                .setRetryCount(1)
                // 设置请求重试时间
                .setRetryTime(2000)
                .into();
        // 默认本地个性化地图初始化方法
        SDKInitializer.setAgreePrivacy(this, true);
        try {
            SDKInitializer.initialize(this);
        } catch (BaiduMapSDKException e) {
            e.printStackTrace();
        }
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }



    public static App getInstance() {
        return instance;
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
