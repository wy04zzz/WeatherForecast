package com.gangan.weather.http.api;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.hjq.http.config.IRequestApi;

import java.io.Serializable;
import java.util.List;

//https://xw.tianqi.qq.com/
public final class WeatherApi implements IRequestApi, Serializable {
    @NonNull
    @Override
    public String getApi() {
        return "weather/common?source=xw&refer=h5&weather_type=air%7Cobserve%7Crise%7Cforecast_1h%7Cforecast_24h%7Cindex%7Calarm%7Climit_forecast%7Ctips%7Clocal_channel%7Cnearby_alarm";
    }

    private String province;
    private String city;
    private String county;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public static class Bean implements Serializable {

        /**
         * air : {"aqi":40,"aqi_level":1,"aqi_name":"优","aqi_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png","co":"1","no2":"17","o3":"125","pm10":"39","pm2.5":"26","pm25":"26","so2":"6","update_time":"202404291600","rank":187,"total":374}
         * limit : null
         * index : {"airconditioner":{"detail":"您将感到很舒适，一般不需要开启空调。","info":"较少开启","name":"空调开启","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/airconditioner.png"},"allergy":{"detail":"天气条件易诱发过敏，宜穿长衣长裤并佩戴好眼镜和口罩，外出归来时及时清洁手和口鼻。","info":"易发","name":"过敏","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/allergy.png"},"carwash":{"detail":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","info":"较适宜","name":"洗车","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/carwash.png"},"chill":{"detail":"温度未达到风寒所需的低温，稍作防寒准备即可。","info":"无","name":"风寒","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/chill.png"},"clothes":{"detail":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","info":"较舒适","name":"穿衣","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/clothes.png"},"cold":{"detail":"虽然温度适宜但风力较大，仍较易发生感冒，体质较弱的朋友请注意适当防护。","info":"较易发","name":"感冒","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/cold.png"},"comfort":{"detail":"白天天气阴沉，风力较强，这种天气条件下，会感到有点儿凉，但大部分人完全可以接受。","info":"较舒适","name":"舒适度","url":""},"diffusion":{"detail":"气象条件有利于空气污染物稀释、扩散和清除。","info":"良","name":"空气污染扩散条件","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/diffusion.png"},"dry":{"detail":"阴天，路面比较干燥，路况较好。","info":"干燥","name":"路况","url":""},"drying":{"detail":"天气阴沉，不利于水分的迅速蒸发，不太适宜晾晒。若需要晾晒，请尽量选择通风的地点。","info":"不太适宜","name":"晾晒","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/drying.png"},"fish":{"detail":"天气不好，有风，不适合垂钓。","info":"不宜","name":"钓鱼","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/fish.png"},"heatstroke":{"detail":"天气舒适，令人神清气爽的一天，不用担心中暑的困扰。","info":"无中暑风险","name":"中暑","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/heatstroke.png"},"makeup":{"detail":"风力不大，建议用中性保湿型霜类化妆品，无需选用防晒化妆品。","info":"保湿","name":"化妆","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/makeup.png"},"mood":{"detail":"天气阴沉，会感觉莫名的压抑，情绪低落，此时将所有的悲喜都静静地沉到心底，在喧嚣的尘世里，感受片刻恬淡的宁静。","info":"较差","name":"心情","url":""},"morning":{"detail":"早晨气象条件较适宜晨练，但天气阴沉，风力稍大，部分路面较湿滑，请选择合适的地点晨练。","info":"较适宜","name":"晨练","url":""},"sports":{"detail":"天气较好，但因风力稍强，户外可选择对风力要求不高的运动，推荐您进行室内运动。","info":"较适宜","name":"运动","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/sports.png"},"sunglasses":{"detail":"白天天空阴沉，但太阳辐射较强，建议佩戴透射比1级且标注UV380-UV400的浅色太阳镜","info":"必要","name":"太阳镜","url":""},"sunscreen":{"detail":"属弱紫外辐射天气，长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","info":"弱","name":"防晒","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/sunscreen.png"},"time":"20240429","tourism":{"detail":"天气较好，风稍大，但温度适宜，总体来说还是好天气。这样的天气适宜旅游，您可以尽情享受大自然的风光。","info":"适宜","name":"旅游","url":""},"traffic":{"detail":"阴天，路面干燥，交通气象条件良好，车辆可以正常行驶。","info":"良好","name":"交通","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/traffic.png"},"ultraviolet":{"detail":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","info":"最弱","name":"紫外线强度","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/ultraviolet.png"},"umbrella":{"detail":"阴天，但降水概率很低，因此您在出门的时候无须带雨伞。","info":"不带伞","name":"雨伞","url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/index/umbrella.png"}}
         * observe : {"degree":"23","humidity":"67","precipitation":"0","pressure":"1007","update_time":"202404291710","weather":"晴","weather_code":"00","weather_short":"晴","wind_direction":"8","wind_power":"4-5","wind_direction_name":"北风","weather_bg_pag":"https://mat1.gtimg.com/qqcdn/xw/tianqi/pag/baitian/beijing/00.pag","weather_pag":"https://mat1.gtimg.com/qqcdn/xw/tianqi/pag/baitian/icon/00.pag","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/bigIcon/baitian/00.png","weather_color":["#4891FF","#9AD2F9","#ADD3ED"],"weather_first":"https://mat1.gtimg.com/qqcdn/xw/tianqi/first/baitian/00.png"}
         * tips : {"observe":{"0":"天有点冷，注意保暖~","1":"现在的温度比较舒适~"},"forecast_24h":{}}
         * alarm : []
         * rise : [{"sunrise":"05:14","sunset":"18:35","time":"20240429"},{"sunrise":"05:13","sunset":"18:36","time":"20240430"},{"sunrise":"05:12","sunset":"18:36","time":"20240501"},{"sunrise":"05:11","sunset":"18:37","time":"20240502"},{"sunrise":"05:10","sunset":"18:38","time":"20240503"},{"sunrise":"05:09","sunset":"18:39","time":"20240504"},{"sunrise":"05:08","sunset":"18:39","time":"20240505"},{"sunrise":"05:08","sunset":"18:40","time":"20240506"},{"sunrise":"05:07","sunset":"18:41","time":"20240507"},{"sunrise":"05:06","sunset":"18:41","time":"20240508"},{"sunrise":"05:05","sunset":"18:42","time":"20240509"},{"sunrise":"05:04","sunset":"18:43","time":"20240510"},{"sunrise":"05:04","sunset":"18:43","time":"20240511"},{"sunrise":"05:03","sunset":"18:44","time":"20240512"},{"sunrise":"05:02","sunset":"18:45","time":"20240513"}]
         * forecast_1h : [{"degree":"21","update_time":"20240429160000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png"},{"degree":"20","update_time":"20240429170000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png"},{"degree":"20","update_time":"20240429180000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"西风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png"},{"degree":"19","update_time":"20240429190000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"南风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"18","update_time":"20240429200000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"18","update_time":"20240429210000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"17","update_time":"20240429220000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"17","update_time":"20240429230000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"16","update_time":"20240430000000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"16","update_time":"20240430010000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"16","update_time":"20240430020000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"15","update_time":"20240430030000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"15","update_time":"20240430040000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"15","update_time":"20240430050000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"degree":"15","update_time":"20240430060000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png"},{"degree":"15","update_time":"20240430070000","weather":"阴","weather_code":"02","weather_short":"阴","wind_direction":"东北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png"},{"degree":"16","update_time":"20240430080000","weather":"多云","weather_code":"01","weather_short":"多云","wind_direction":"东北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/01.png"},{"degree":"16","update_time":"20240430090000","weather":"小雨","weather_code":"07","weather_short":"小雨","wind_direction":"东北风","wind_power":"4-5","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/07.png"},{"degree":"16","update_time":"20240430100000","weather":"小雨","weather_code":"07","weather_short":"小雨","wind_direction":"东北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/07.png"},{"degree":"16","update_time":"20240430110000","weather":"小雨","weather_code":"07","weather_short":"小雨","wind_direction":"东北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/07.png"},{"degree":"15","update_time":"20240430120000","weather":"小雨","weather_code":"07","weather_short":"小雨","wind_direction":"东北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/07.png"},{"degree":"15","update_time":"20240430130000","weather":"小雨","weather_code":"07","weather_short":"小雨","wind_direction":"东北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/07.png"},{"degree":"15","update_time":"20240430140000","weather":"小雨","weather_code":"07","weather_short":"小雨","wind_direction":"东北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/07.png"},{"degree":"15","update_time":"20240430150000","weather":"中雨","weather_code":"08","weather_short":"中雨","wind_direction":"东北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/08.png"},{"degree":"14","update_time":"20240430160000","weather":"中雨","weather_code":"08","weather_short":"中雨","wind_direction":"东北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/08.png"},{"degree":"14","update_time":"20240430170000","weather":"中雨","weather_code":"08","weather_short":"中雨","wind_direction":"东北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/08.png"},{"degree":"14","update_time":"20240430180000","weather":"中雨","weather_code":"08","weather_short":"中雨","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/08.png"},{"degree":"13","update_time":"20240430190000","weather":"中雨","weather_code":"08","weather_short":"中雨","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/08.png"},{"degree":"13","update_time":"20240430200000","weather":"中雨","weather_code":"08","weather_short":"中雨","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/08.png"},{"degree":"13","update_time":"20240430210000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"degree":"13","update_time":"20240430220000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"degree":"13","update_time":"20240430230000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"degree":"13","update_time":"20240501000000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"东南风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"degree":"13","update_time":"20240501010000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"西南风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"degree":"13","update_time":"20240501020000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"degree":"12","update_time":"20240501030000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"degree":"12","update_time":"20240501040000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"西北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"degree":"12","update_time":"20240501050000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"西北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"degree":"12","update_time":"20240501060000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"西北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/03.png"},{"degree":"13","update_time":"20240501070000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"西北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/03.png"},{"degree":"14","update_time":"20240501080000","weather":"阵雨","weather_code":"03","weather_short":"阵雨","wind_direction":"西北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/03.png"},{"degree":"15","update_time":"20240501090000","weather":"多云","weather_code":"01","weather_short":"多云","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/01.png"},{"degree":"17","update_time":"20240501100000","weather":"多云","weather_code":"01","weather_short":"多云","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/01.png"},{"degree":"19","update_time":"20240501110000","weather":"晴","weather_code":"00","weather_short":"晴","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/00.png"},{"degree":"19","update_time":"20240501120000","weather":"晴","weather_code":"00","weather_short":"晴","wind_direction":"西南风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/00.png"},{"degree":"20","update_time":"20240501130000","weather":"晴","weather_code":"00","weather_short":"晴","wind_direction":"东南风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/00.png"},{"degree":"21","update_time":"20240501140000","weather":"晴","weather_code":"00","weather_short":"晴","wind_direction":"北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/00.png"},{"degree":"21","update_time":"20240501150000","weather":"晴","weather_code":"00","weather_short":"晴","wind_direction":"东北风","wind_power":"3-4","weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/00.png"}]
         * forecast_24h : [{"day_weather":"阴","day_weather_code":"02","day_weather_short":"阴","day_wind_direction":"东风","day_wind_direction_code":"2","day_wind_power":"3-4","day_wind_power_code":"1","min_degree":"18","max_degree":"21","night_weather":"小雨","night_weather_code":"07","night_weather_short":"小雨","night_wind_direction":"北风","night_wind_direction_code":"8","night_wind_power":"4-5","night_wind_power_code":"2","time":"2024-04-28","aqi":17,"aqi_level":1,"aqi_name":"优","aqi_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png","day_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png","night_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/07.png"},{"day_weather":"阴","day_weather_code":"02","day_weather_short":"阴","day_wind_direction":"西北风","day_wind_direction_code":"7","day_wind_power":"4-5","day_wind_power_code":"2","min_degree":"15","max_degree":"22","night_weather":"阴","night_weather_code":"02","night_weather_short":"阴","night_wind_direction":"东北风","night_wind_direction_code":"1","night_wind_power":"4-5","night_wind_power_code":"2","time":"2024-04-29","aqi":40,"aqi_level":1,"aqi_name":"优","aqi_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png","day_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png","night_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/02.png"},{"day_weather":"中到大雨","day_weather_code":"22","day_weather_short":"大雨","day_wind_direction":"东北风","day_wind_direction_code":"1","day_wind_power":"3-4","day_wind_power_code":"1","min_degree":"11","max_degree":"16","night_weather":"阵雨","night_weather_code":"03","night_weather_short":"阵雨","night_wind_direction":"北风","night_wind_direction_code":"8","night_wind_power":"3-4","night_wind_power_code":"1","time":"2024-04-30","aqi":28,"aqi_level":1,"aqi_name":"优","aqi_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png","day_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/22.png","night_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/03.png"},{"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"北风","day_wind_direction_code":"8","day_wind_power":"3-4","day_wind_power_code":"1","min_degree":"11","max_degree":"21","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"北风","night_wind_direction_code":"8","night_wind_power":"3-4","night_wind_power_code":"1","time":"2024-05-01","aqi":36,"aqi_level":1,"aqi_name":"优","aqi_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png","day_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/00.png","night_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/01.png"},{"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"东风","day_wind_direction_code":"2","day_wind_power":"3-4","day_wind_power_code":"1","min_degree":"14","max_degree":"21","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"东风","night_wind_direction_code":"2","night_wind_power":"3-4","night_wind_power_code":"1","time":"2024-05-02","aqi":36,"aqi_level":1,"aqi_name":"优","aqi_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png","day_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/01.png","night_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/01.png"},{"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"东风","day_wind_direction_code":"2","day_wind_power":"3-4","day_wind_power_code":"1","min_degree":"13","max_degree":"22","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"东风","night_wind_direction_code":"2","night_wind_power":"3-4","night_wind_power_code":"1","time":"2024-05-03","aqi":32,"aqi_level":1,"aqi_name":"优","aqi_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png","day_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/00.png","night_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/01.png"},{"day_weather":"阴","day_weather_code":"02","day_weather_short":"阴","day_wind_direction":"东南风","day_wind_direction_code":"3","day_wind_power":"4-5","day_wind_power_code":"2","min_degree":"17","max_degree":"23","night_weather":"中雨","night_weather_code":"08","night_weather_short":"中雨","night_wind_direction":"东南风","night_wind_direction_code":"3","night_wind_power":"4-5","night_wind_power_code":"2","time":"2024-05-04","aqi":45,"aqi_level":1,"aqi_name":"优","aqi_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png","day_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png","night_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/08.png"},{"day_weather":"中雨","day_weather_code":"08","day_weather_short":"中雨","day_wind_direction":"南风","day_wind_direction_code":"4","day_wind_power":"4-5","day_wind_power_code":"2","min_degree":"19","max_degree":"23","night_weather":"小雨","night_weather_code":"07","night_weather_short":"小雨","night_wind_direction":"西风","night_wind_direction_code":"6","night_wind_power":"3-4","night_wind_power_code":"1","time":"2024-05-05","aqi":0,"aqi_level":1,"aqi_name":"优","aqi_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png","day_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/08.png","night_weather_url":"https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/07.png"}]
         * air_forecast : []
         * limit_forecast : []
         * local_channel : news_news_suz
         * observe_mock : null
         * nearby_alarm : []
         */

        private AirBean air;
        private ObserveBean observe;
        private TipsBean tips;
        private List<Forecast1hBean> forecast_1h;
        private List<Forecast24hBeanX> forecast_24h;

        public AirBean getAir() {
            return air;
        }

        public void setAir(AirBean air) {
            this.air = air;
        }

        public ObserveBean getObserve() {
            return observe;
        }

        public void setObserve(ObserveBean observe) {
            this.observe = observe;
        }

        public TipsBean getTips() {
            return tips;
        }

        public void setTips(TipsBean tips) {
            this.tips = tips;
        }

        public List<Forecast1hBean> getForecast_1h() {
            return forecast_1h;
        }

        public void setForecast_1h(List<Forecast1hBean> forecast_1h) {
            this.forecast_1h = forecast_1h;
        }

        public List<Forecast24hBeanX> getForecast_24h() {
            return forecast_24h;
        }

        public void setForecast_24h(List<Forecast24hBeanX> forecast_24h) {
            this.forecast_24h = forecast_24h;
        }

        public static class AirBean implements Serializable {
            /**
             * aqi : 40
             * aqi_level : 1
             * aqi_name : 优
             * aqi_url : https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png
             * co : 1
             * no2 : 17
             * o3 : 125
             * pm10 : 39
             * pm2.5 : 26
             * pm25 : 26
             * so2 : 6
             * update_time : 202404291600
             * rank : 187
             * total : 374
             */

            private int aqi;
            private int aqi_level;
            private String aqi_name;
            private String aqi_url;
            private String co;
            private String no2;
            private String o3;
            private String pm10;
            @SerializedName("pm2.5")
            private String _$Pm25126; // FIXME check this code
            private String pm25;
            private String so2;
            private String update_time;
            private int rank;
            private int total;

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public int getAqi_level() {
                return aqi_level;
            }

            public void setAqi_level(int aqi_level) {
                this.aqi_level = aqi_level;
            }

            public String getAqi_name() {
                return aqi_name;
            }

            public void setAqi_name(String aqi_name) {
                this.aqi_name = aqi_name;
            }

            public String getAqi_url() {
                return aqi_url;
            }

            public void setAqi_url(String aqi_url) {
                this.aqi_url = aqi_url;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String get_$Pm25126() {
                return _$Pm25126;
            }

            public void set_$Pm25126(String _$Pm25126) {
                this._$Pm25126 = _$Pm25126;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }


        public static class ObserveBean implements Serializable {
            /**
             * degree : 23
             * humidity : 67
             * precipitation : 0
             * pressure : 1007
             * update_time : 202404291710
             * weather : 晴
             * weather_code : 00
             * weather_short : 晴
             * wind_direction : 8
             * wind_power : 4-5
             * wind_direction_name : 北风
             * weather_bg_pag : https://mat1.gtimg.com/qqcdn/xw/tianqi/pag/baitian/beijing/00.pag
             * weather_pag : https://mat1.gtimg.com/qqcdn/xw/tianqi/pag/baitian/icon/00.pag
             * weather_url : https://mat1.gtimg.com/qqcdn/xw/tianqi/bigIcon/baitian/00.png
             * weather_color : ["#4891FF","#9AD2F9","#ADD3ED"]
             * weather_first : https://mat1.gtimg.com/qqcdn/xw/tianqi/first/baitian/00.png
             */

            private String degree;
            private String humidity;
            private String precipitation;
            private String pressure;
            private String update_time;
            private String weather;
            private String weather_code;
            private String weather_short;
            private String wind_direction;
            private String wind_power;
            private String wind_direction_name;
            private String weather_bg_pag;
            private String weather_pag;
            private String weather_url;
            private String weather_first;
            private List<String> weather_color;

            public String getDegree() {
                return degree;
            }

            public void setDegree(String degree) {
                this.degree = degree;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(String precipitation) {
                this.precipitation = precipitation;
            }

            public String getPressure() {
                return pressure;
            }

            public void setPressure(String pressure) {
                this.pressure = pressure;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getWeather_code() {
                return weather_code;
            }

            public void setWeather_code(String weather_code) {
                this.weather_code = weather_code;
            }

            public String getWeather_short() {
                return weather_short;
            }

            public void setWeather_short(String weather_short) {
                this.weather_short = weather_short;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_power() {
                return wind_power;
            }

            public void setWind_power(String wind_power) {
                this.wind_power = wind_power;
            }

            public String getWind_direction_name() {
                return wind_direction_name;
            }

            public void setWind_direction_name(String wind_direction_name) {
                this.wind_direction_name = wind_direction_name;
            }

            public String getWeather_bg_pag() {
                return weather_bg_pag;
            }

            public void setWeather_bg_pag(String weather_bg_pag) {
                this.weather_bg_pag = weather_bg_pag;
            }

            public String getWeather_pag() {
                return weather_pag;
            }

            public void setWeather_pag(String weather_pag) {
                this.weather_pag = weather_pag;
            }

            public String getWeather_url() {
                return weather_url;
            }

            public void setWeather_url(String weather_url) {
                this.weather_url = weather_url;
            }

            public String getWeather_first() {
                return weather_first;
            }

            public void setWeather_first(String weather_first) {
                this.weather_first = weather_first;
            }

            public List<String> getWeather_color() {
                return weather_color;
            }

            public void setWeather_color(List<String> weather_color) {
                this.weather_color = weather_color;
            }
        }

        public static class TipsBean implements Serializable {
            /**
             * observe : {"0":"天有点冷，注意保暖~","1":"现在的温度比较舒适~"}
             * forecast_24h : {}
             */

            private ObserveBeanX observe;
            private Forecast24hBean forecast_24h;

            public ObserveBeanX getObserve() {
                return observe;
            }

            public void setObserve(ObserveBeanX observe) {
                this.observe = observe;
            }

            public Forecast24hBean getForecast_24h() {
                return forecast_24h;
            }

            public void setForecast_24h(Forecast24hBean forecast_24h) {
                this.forecast_24h = forecast_24h;
            }

            public static class ObserveBeanX implements Serializable {
                /**
                 * 0 : 天有点冷，注意保暖~
                 * 1 : 现在的温度比较舒适~
                 */

                @SerializedName("0")
                private String _$0;
                @SerializedName("1")
                private String _$1;

                public String get_$0() {
                    return _$0;
                }

                public void set_$0(String _$0) {
                    this._$0 = _$0;
                }

                public String get_$1() {
                    return _$1;
                }

                public void set_$1(String _$1) {
                    this._$1 = _$1;
                }
            }

            public static class Forecast24hBean implements Serializable {
            }
        }


        public static class Forecast1hBean implements Serializable {
            /**
             * degree : 21
             * update_time : 20240429160000
             * weather : 阴
             * weather_code : 02
             * weather_short : 阴
             * wind_direction : 北风
             * wind_power : 4-5
             * weather_url : https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png
             */

            private String degree;
            private String update_time;
            private String weather;
            private String weather_code;
            private String weather_short;
            private String wind_direction;
            private String wind_power;
            private String weather_url;

            public String getDegree() {
                return degree;
            }

            public void setDegree(String degree) {
                this.degree = degree;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getWeather_code() {
                return weather_code;
            }

            public void setWeather_code(String weather_code) {
                this.weather_code = weather_code;
            }

            public String getWeather_short() {
                return weather_short;
            }

            public void setWeather_short(String weather_short) {
                this.weather_short = weather_short;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_power() {
                return wind_power;
            }

            public void setWind_power(String wind_power) {
                this.wind_power = wind_power;
            }

            public String getWeather_url() {
                return weather_url;
            }

            public void setWeather_url(String weather_url) {
                this.weather_url = weather_url;
            }
        }

        public static class Forecast24hBeanX implements Serializable {
            /**
             * day_weather : 阴
             * day_weather_code : 02
             * day_weather_short : 阴
             * day_wind_direction : 东风
             * day_wind_direction_code : 2
             * day_wind_power : 3-4
             * day_wind_power_code : 1
             * min_degree : 18
             * max_degree : 21
             * night_weather : 小雨
             * night_weather_code : 07
             * night_weather_short : 小雨
             * night_wind_direction : 北风
             * night_wind_direction_code : 8
             * night_wind_power : 4-5
             * night_wind_power_code : 2
             * time : 2024-04-28
             * aqi : 17
             * aqi_level : 1
             * aqi_name : 优
             * aqi_url : https://mat1.gtimg.com/qqcdn/xw/tianqi/air/1.png
             * day_weather_url : https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/baitian/02.png
             * night_weather_url : https://mat1.gtimg.com/qqcdn/xw/tianqi/smallIcon/heiye/07.png
             */

            private String day_weather;
            private String day_weather_code;
            private String day_weather_short;
            private String day_wind_direction;
            private String day_wind_direction_code;
            private String day_wind_power;
            private String day_wind_power_code;
            private String min_degree;
            private String max_degree;
            private String night_weather;
            private String night_weather_code;
            private String night_weather_short;
            private String night_wind_direction;
            private String night_wind_direction_code;
            private String night_wind_power;
            private String night_wind_power_code;
            private String time;
            private int aqi;
            private int aqi_level;
            private String aqi_name;
            private String aqi_url;
            private String day_weather_url;
            private String night_weather_url;

            public String getDay_weather() {
                return day_weather;
            }

            public void setDay_weather(String day_weather) {
                this.day_weather = day_weather;
            }

            public String getDay_weather_code() {
                return day_weather_code;
            }

            public void setDay_weather_code(String day_weather_code) {
                this.day_weather_code = day_weather_code;
            }

            public String getDay_weather_short() {
                return day_weather_short;
            }

            public void setDay_weather_short(String day_weather_short) {
                this.day_weather_short = day_weather_short;
            }

            public String getDay_wind_direction() {
                return day_wind_direction;
            }

            public void setDay_wind_direction(String day_wind_direction) {
                this.day_wind_direction = day_wind_direction;
            }

            public String getDay_wind_direction_code() {
                return day_wind_direction_code;
            }

            public void setDay_wind_direction_code(String day_wind_direction_code) {
                this.day_wind_direction_code = day_wind_direction_code;
            }

            public String getDay_wind_power() {
                return day_wind_power;
            }

            public void setDay_wind_power(String day_wind_power) {
                this.day_wind_power = day_wind_power;
            }

            public String getDay_wind_power_code() {
                return day_wind_power_code;
            }

            public void setDay_wind_power_code(String day_wind_power_code) {
                this.day_wind_power_code = day_wind_power_code;
            }

            public String getMin_degree() {
                return min_degree;
            }

            public void setMin_degree(String min_degree) {
                this.min_degree = min_degree;
            }

            public String getMax_degree() {
                return max_degree;
            }

            public void setMax_degree(String max_degree) {
                this.max_degree = max_degree;
            }

            public String getNight_weather() {
                return night_weather;
            }

            public void setNight_weather(String night_weather) {
                this.night_weather = night_weather;
            }

            public String getNight_weather_code() {
                return night_weather_code;
            }

            public void setNight_weather_code(String night_weather_code) {
                this.night_weather_code = night_weather_code;
            }

            public String getNight_weather_short() {
                return night_weather_short;
            }

            public void setNight_weather_short(String night_weather_short) {
                this.night_weather_short = night_weather_short;
            }

            public String getNight_wind_direction() {
                return night_wind_direction;
            }

            public void setNight_wind_direction(String night_wind_direction) {
                this.night_wind_direction = night_wind_direction;
            }

            public String getNight_wind_direction_code() {
                return night_wind_direction_code;
            }

            public void setNight_wind_direction_code(String night_wind_direction_code) {
                this.night_wind_direction_code = night_wind_direction_code;
            }

            public String getNight_wind_power() {
                return night_wind_power;
            }

            public void setNight_wind_power(String night_wind_power) {
                this.night_wind_power = night_wind_power;
            }

            public String getNight_wind_power_code() {
                return night_wind_power_code;
            }

            public void setNight_wind_power_code(String night_wind_power_code) {
                this.night_wind_power_code = night_wind_power_code;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public int getAqi_level() {
                return aqi_level;
            }

            public void setAqi_level(int aqi_level) {
                this.aqi_level = aqi_level;
            }

            public String getAqi_name() {
                return aqi_name;
            }

            public void setAqi_name(String aqi_name) {
                this.aqi_name = aqi_name;
            }

            public String getAqi_url() {
                return aqi_url;
            }

            public void setAqi_url(String aqi_url) {
                this.aqi_url = aqi_url;
            }

            public String getDay_weather_url() {
                return day_weather_url;
            }

            public void setDay_weather_url(String day_weather_url) {
                this.day_weather_url = day_weather_url;
            }

            public String getNight_weather_url() {
                return night_weather_url;
            }

            public void setNight_weather_url(String night_weather_url) {
                this.night_weather_url = night_weather_url;
            }
        }
    }
}