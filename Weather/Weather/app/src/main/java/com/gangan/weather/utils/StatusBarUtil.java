package com.gangan.weather.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

public class StatusBarUtil {
    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    public static void setTransparent(Activity activity,boolean textColor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity,textColor);
    }

    /**
     * 设置背景透明
     * @param activity
     * @param textColor 状态栏字体颜色 true 黑色，false 白色
     */
    private static void transparentStatusBar(Activity activity,boolean textColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE|(textColor?View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR:View.SYSTEM_UI_FLAG_VISIBLE);
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);//透明

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

    }
}
