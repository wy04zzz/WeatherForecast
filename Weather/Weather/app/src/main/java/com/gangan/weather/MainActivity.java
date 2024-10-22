package com.gangan.weather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gangan.weather.adapter.MyWeather1HListRecyclerAdapter;
import com.gangan.weather.adapter.MyWeather24HListRecyclerAdapter;
import com.gangan.weather.databinding.ActivityMainBinding;
import com.gangan.weather.http.api.WeatherApi;
import com.gangan.weather.http.model.HttpData;
import com.gangan.weather.utils.AppManager;
import com.gangan.weather.utils.ApplyPermissionUtil;
import com.gangan.weather.utils.DateUtils;
import com.gangan.weather.utils.SPUtils;
import com.gangan.weather.utils.StatusBarUtil;
import com.gangan.weather.utils.Utils;
import com.google.gson.Gson;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallbackProxy;
import com.hjq.http.listener.OnHttpListener;
import com.kongzue.dialogx.citypicker.CityPickerDialog;
import com.kongzue.dialogx.citypicker.interfaces.OnCitySelected;
import com.kongzue.dialogx.dialogs.CustomDialog;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.dialogs.PopTip;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyWeather1HListRecyclerAdapter adapter1h;
    private List<WeatherApi.Bean.Forecast1hBean> mList1h = new ArrayList<>();
    private MyWeather24HListRecyclerAdapter adapter24h;
    private List<WeatherApi.Bean.Forecast24hBeanX> mList24h = new ArrayList<>();
    String defaultProvince = "北京市", defaultCity = "北京市", defaultDistrict = "海淀区";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this); //添加到栈中
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StatusBarUtil.setTransparent(this,false);
        String user = (String) SPUtils.get(this, SPUtils.LOGIN_NAME, "");
        if (TextUtils.isEmpty(user)) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        adapter1h = new MyWeather1HListRecyclerAdapter(mList1h, this);
        binding.recycler1h.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recycler1h.setAdapter(adapter1h);
        adapter1h.setOnItemClickListener(new MyWeather1HListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                show1hDetails(position);
            }
        });

        adapter24h = new MyWeather24HListRecyclerAdapter(mList24h, this);
        binding.recycler24h.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler24h.setAdapter(adapter24h);
        adapter24h.setOnItemClickListener(new MyWeather24HListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                show24hDetails(position);
            }
        });
        //地图授权隐私协议
        LocationClient.setAgreePrivacy(true);
        permission();
        binding.tvLoc.setText(defaultDistrict);
        WeatherApi(defaultProvince,defaultCity,defaultDistrict);
        binding.tvLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPickerDialog.build()
                        .setDefaultSelect(defaultProvince, defaultCity, defaultDistrict)
                        .show(new OnCitySelected() {
                            @Override
                            public void onSelect(String text, String province, String city, String district) {
                                defaultProvince = province;
                                defaultCity = city;
                                defaultDistrict = district;
                                binding.tvLoc.setText(defaultDistrict);
                                WeatherApi(defaultProvince,defaultCity,defaultDistrict);
                            }
                        });
            }
        });
        binding.ivMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MusicListActivity.class));
            }
        });
        binding.ivCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
            }
        });
        binding.ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }

    private void show1hDetails(int pos) {
        MessageDialog.show(mList1h.get(pos).getUpdate_time().substring(8,10)+":00", "", "确定")
                .setCustomView(new OnBindView<MessageDialog>(R.layout.layout_1h_dialog) {
                    @Override
                    public void onBind(MessageDialog dialog, View v) {
                        ImageView imageView = v.findViewById(R.id.iv_pic);
                        TextView tvTemp = v.findViewById(R.id.tv_temp);
                        TextView tvWeather = v.findViewById(R.id.tv_weather);
                        TextView tvWind = v.findViewById(R.id.tv_wind);
                        Glide.with(MainActivity.this)
                                .load(mList1h.get(pos).getWeather_url())
                                .into(imageView);
                        tvWeather.setText(mList1h.get(pos).getWeather());
                        tvTemp.setText(mList1h.get(pos).getDegree() + "°");
                        tvWind.setText(mList1h.get(pos).getWind_direction()+" "+mList1h.get(pos).getWind_power()+"级");
                    }
                });
    }
    private void show24hDetails(int pos) {
        MessageDialog.show(pos==0?"昨天":pos==1?"今天": DateUtils.getDayOfWeek(mList24h.get(pos).getTime()), mList24h.get(pos).getTime(), "确定")
                .setCustomView(new OnBindView<MessageDialog>(R.layout.layout_24h_dialog) {
                    @Override
                    public void onBind(MessageDialog dialog, View v) {
                        ImageView imageView = v.findViewById(R.id.iv_pic);
                        TextView tvWeather = v.findViewById(R.id.tv_weather);
                        TextView tvWind = v.findViewById(R.id.tv_wind);
                        ImageView imageView_night = v.findViewById(R.id.iv_pic_night);
                        TextView tvWeather_night = v.findViewById(R.id.tv_weather_night);
                        TextView tvWind_night = v.findViewById(R.id.tv_wind_night);

                        TextView tvTemp = v.findViewById(R.id.tv_temp);
                        TextView tvAqi = v.findViewById(R.id.tv_aqi);
                        ImageView ivAqi = v.findViewById(R.id.iv_aqi);

                        Glide.with(MainActivity.this)
                                .load(mList24h.get(pos).getDay_weather_url())
                                .into(imageView);
                        tvWeather.setText(mList24h.get(pos).getDay_weather());
                        tvWind.setText(mList24h.get(pos).getDay_wind_direction()+" "+mList24h.get(pos).getDay_wind_power()+"级");
                        Glide.with(MainActivity.this)
                                .load(mList24h.get(pos).getNight_weather_url())
                                .into(imageView_night);
                        tvWeather_night.setText(mList24h.get(pos).getNight_weather());
                        tvWind_night.setText(mList24h.get(pos).getNight_wind_direction()+" "+mList24h.get(pos).getNight_wind_power()+"级");

                        tvTemp.setText(mList24h.get(pos).getMax_degree()+"°/"+mList24h.get(pos).getMin_degree()+"°");
                        tvAqi.setText(mList24h.get(pos).getAqi_name()+" "+mList24h.get(pos).getAqi());
                        Glide.with(MainActivity.this)
                                .load(mList24h.get(pos).getAqi_url())
                                .into(ivAqi);
                    }
                });
    }
    private void permission() {
        ApplyPermissionUtil.newUtil().requestPermissions(MainActivity.this, new ApplyPermissionUtil.onRxPermissionListener() {
            @Override
            public void onRequest(int isGranted) {
                if (isGranted==0) {
                    if (Build.VERSION.SDK_INT >= 29) {
                        ApplyPermissionUtil.newUtil().requestPermissions(MainActivity.this, new ApplyPermissionUtil.onRxPermissionListener() {
                            @Override
                            public void onRequest(int isGranted) {
                                if (isGranted == 0) {
                                    init();
                                } else {
                                    MessageDialog.show("警告", isGranted == 1 ? "请同意权限，否则无法使用！" : "请前往应用设置开启权限，否则无法使用", "确定")
                                            .setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
                                                @Override
                                                public boolean onClick(MessageDialog baseDialog, View v) {
                                                    permission();
                                                    return false;
                                                }
                                            });
                                }
                            }
                        }, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
                    } else {
                        init();
                    }

                } else {
                    MessageDialog.show("警告", isGranted==1?"请同意权限，否则无法使用！":"请前往应用设置开启权限，否则无法使用", "确定")
                            .setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
                                @Override
                                public boolean onClick(MessageDialog baseDialog, View v) {
                                    permission();
                                    return false;
                                }
                            });
                }
            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR);

        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                MessageDialog.show("提示", "请启用文件访问权限，否则此应用程序无法正常使用！", "确认")
                        .setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
                            @Override
                            public boolean onClick(MessageDialog baseDialog, View v) {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                                return false;
                            }
                        });
            }
        } else {
            ApplyPermissionUtil.newUtil().requestPermissions(this, new ApplyPermissionUtil.onRxPermissionListener() {
                @Override
                public void onRequest(int isGranted) {

                }
            },android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void init() {
        //如果没开启GPS进行提示
        if (!Utils.getGpsStatus(this)) {
            MessageDialog.show("提示", "需要开启定位服务！", "去开启")
                    .setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
                        @Override
                        public boolean onClick(MessageDialog baseDialog, View v) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                            return false;
                        }
                    });
        }

        initOnceSite(this,mListener);
    }

    private void WeatherApi(String province,String city,String county) {
        WeatherApi api = new WeatherApi();
        api.setProvince(province);
        api.setCity(city);
        api.setCounty(county);
        EasyHttp.get(MainActivity.this)
                .api(api)
                .request(new HttpCallbackProxy<HttpData<WeatherApi.Bean>>(null) {
                    @Override
                    public void onHttpSuccess(HttpData<WeatherApi.Bean> result) {
                        //背景
                        binding.main.getShapeDrawableBuilder().setSolidGradientColors(Color.parseColor(result.getData().getObserve().getWeather_color().get(0)), Color.parseColor(result.getData().getObserve().getWeather_color().get(1)),Color.parseColor(result.getData().getObserve().getWeather_color().get(2))).intoBackground();
                        //今天天气主图
                        Glide.with(MainActivity.this)
                                .load(result.getData().getObserve().getWeather_url())
                                .into(binding.ivMainPic);
                        //今日实时温度
                        binding.tvWeatherDegree.setText(result.getData().getObserve().getDegree()+"°");
                        //今日空气质量
                        binding.tvAqi.setText(result.getData().getAir().getAqi_name()+" "+result.getData().getAir().getAqi());
                        //今日空气质量图标
                        Glide.with(MainActivity.this)
                                .load(result.getData().getAir().getAqi_url())
                                .into(binding.ivAqi);

                        //今日湿度
                        binding.tvWeatherHumidity.setText("湿度 "+result.getData().getObserve().getHumidity()+"%");
                        //今日风向
                        binding.tvWindDirection.setText(result.getData().getObserve().getWind_direction_name()+" "+result.getData().getObserve().getWind_power()+"级");

                        mList1h.clear();
                        mList1h.addAll(result.getData().getForecast_1h());
                        adapter1h.notifyDataSetChanged();

                        mList24h.clear();
                        mList24h.addAll(result.getData().getForecast_24h());
                        adapter24h.notifyDataSetChanged();

                    }
                    @Override
                    public void onHttpStart(Call call) {
                        WaitDialog.show("加载中");
                    }


                    @Override
                    public void onHttpFail(Throwable throwable) {
                        PopTip.show(throwable.getMessage()).iconError();

                    }

                    @Override
                    public void onHttpEnd(Call call) {
                        WaitDialog.dismiss();
                    }
                });
    }

    //启动单次定位
    public void initOnceSite(Context context, BDAbstractLocationListener listener) {
        try {
            final LocationClient locClientOne = new LocationClient(context);
            locClientOne.registerLocationListener(listener);
            //设置定位参数
            LocationClientOption option = new LocationClientOption();
            //可选，设置定位模式，默认高精度 LocationMode.Hight_Accuracy：高精度；
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            //设置是否进行单次定位，单次定位时调用start之后会默认返回一次定位结果
            option.setOnceLocation(true);
            //设置是否需要地址信息
            option.setIsNeedAddress(true);
            option.setIsNeedLocationDescribe(true);
            option.setCoorType( "bd09ll" ); // 可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            option.setNeedDeviceDirect(false); // 可选，设置是否需要设备方向结果
            //设置定位参数
            locClientOne.setLocOption(option);
            //开启定位
            locClientOne.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    /*****
     *
     * 定位结果回调
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {

                boolean isSuccess = false;
                String info = "";
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    info = "gps定位成功";
                    isSuccess = true;
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    info = "网络定位成功";
                    isSuccess = true;
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    info = "离线定位成功，离线定位结果也是有效的";
                    isSuccess = true;
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    info = "服务端网络定位失败";
                    isSuccess = false;
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    info = "网络不同导致定位失败";
                    isSuccess = false;
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    info = "无法获取有效定位依据导致定位失败";
                    isSuccess = false;
                }
                if (isSuccess) {
                    Log.e("onReceiveLocation", info);
                    Log.e("sdasd", location.getProvince());
                    Log.e("sdasd", location.getCity());
                    Log.e("sdasd", location.getDistrict());
                    WeatherApi(location.getProvince(),location.getCity(),location.getDistrict());
                    defaultProvince = location.getProvince();
                    defaultCity = location.getCity();
                    defaultDistrict = location.getDistrict();
                    binding.tvLoc.setText(defaultDistrict);
                }

            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            super.onConnectHotSpotMessage(s, i);
        }

        /**
         * 回调定位诊断信息
         */
        @Override
        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage);
            Log.e("onLocDiagnosticMessage", locType + "," + diagnosticType+","+diagnosticMessage);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this); //从栈中移除
    }
}