package com.gangan.weather.http.api;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestServer;

import java.io.Serializable;

//https://dataiqs.com/api/docs/?id=2
public final class MusicApi implements IRequestApi, IRequestServer, Serializable {
    @NonNull
    @Override
    public String getApi() {
        return "api/netease/music";
    }
    @NonNull
    @Override
    public String getHost() {
        return "https://dataiqs.com/";
    }

    private String msg;
    private String type;
    private int id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class Bean implements Serializable {


        /**
         * id : 1293904916
         * name : 童年
         * singername : 群星
         */

        private int id;
        private String name;
        private String singername;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSingername() {
            return singername;
        }

        public void setSingername(String singername) {
            this.singername = singername;
        }
    }
}