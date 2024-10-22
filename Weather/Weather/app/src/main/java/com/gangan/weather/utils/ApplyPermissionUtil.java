package com.gangan.weather.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions3.RxPermissions;


/**
 * 申请权限工具类
 */
public class ApplyPermissionUtil {

   private RxPermissions rxPermission;

   private String TAG = getClass().getName();


   public static ApplyPermissionUtil newUtil(){
      return new ApplyPermissionUtil();
   }

   public void requestPermissions(Activity activity,onRxPermissionListener listener, String... permissions){

      if (rxPermission == null){
         rxPermission = new RxPermissions((FragmentActivity) activity);
      }

      rxPermission.requestEachCombined(permissions)
              .subscribe(permission -> { // will emit 2 Permission objects
                 if (permission.granted) {
                    // 用户已经同意该权限
                    Log.d(TAG, permission.name + " is granted.");
                     if (listener != null) {
                         listener.onRequest(0);
                     }
                 } else if (permission.shouldShowRequestPermissionRationale) {
                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                    Log.d(TAG, permission.name + " is denied. More info should be provided.");
                     if (listener != null) {
                         listener.onRequest(1);
                     }
                 } else {
                     if (listener != null) {
                         listener.onRequest(2);
                     }
                    // 用户拒绝了该权限，并且选中『不再询问』
                    Log.d(TAG, permission.name + " is denied.");
                     Toast.makeText(activity,"请在设置中开启该权限",Toast.LENGTH_SHORT).show();
                 }
              });

   }

   public interface onRxPermissionListener{
       //0,已同意，1，用户拒绝了该权限，没有选中『不再询问』，2用户拒绝
       void onRequest(int isGranted);
   }

}
