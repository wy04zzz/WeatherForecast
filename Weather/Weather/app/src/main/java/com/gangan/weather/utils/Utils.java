package com.gangan.weather.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;


public class Utils {

   /**
    * GPS开关状态
    * @param ctx
    * @return
    */
   public static boolean getGpsStatus(Context ctx){
      //从系统服务中获取定位管理器
      LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
      return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
   }
}
