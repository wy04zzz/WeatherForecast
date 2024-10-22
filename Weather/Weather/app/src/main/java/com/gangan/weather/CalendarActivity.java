package com.gangan.weather;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gangan.weather.adapter.MyListRecyclerAdapter;
import com.gangan.weather.bean.EventDataBean;
import com.gangan.weather.databinding.ActivityCalendarBinding;
import com.gangan.weather.utils.DateUtils;
import com.gangan.weather.utils.SPUtils;
import com.gangan.weather.view.calendarview.custom.CustomWeekBar;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.dialogs.PopMenu;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack;
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener;
import com.kyle.calendarprovider.calendar.CalendarProviderManager;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarActivity extends BaseActivity {

    private ActivityCalendarBinding binding;
    private int mYear;
    private String selectDay;//选择的日期
    private MyListRecyclerAdapter adapter;//列表适配器
    private List<EventDataBean> mList = new ArrayList<>();//列表集合
    private long userId;//用户id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectDay = DateUtils.getFormatyyyyMMdd(System.currentTimeMillis());
        userId = (long) SPUtils.get(CalendarActivity.this, SPUtils.USER_ID, 0L);
        //初始化日历
        initCalendarView();
        //初始化其他ui
        initView();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //获取选中日期的数据库数据
        getEventData();
        //加载日历上的标记
        loadData();
    }

    private void getEventData() {
        mList = LitePal.where("userId = ? and time >= ? and time <= ?", userId + "", DateUtils.getStringToLong(selectDay + " 00:00:00") + "", DateUtils.getStringToLong(selectDay + " 23:59:59") + "").find(EventDataBean.class);
        adapter.updateData(mList);
    }


    private void initCalendarView() {
        mYear = binding.calendarView.getCurYear();
        binding.tvYear.setText(String.valueOf(mYear));
        binding.tvMonthDay.setText(getString(R.string.date_month_day, binding.calendarView.getCurMonth()+"", binding.calendarView.getCurDay()+""));
        binding.tvLunar.setText("今日");
        binding.tvCurrentDay.setText(String.valueOf(binding.calendarView.getCurDay()));
        binding.calendarView.setWeekStarWithSun();
        binding.calendarView.setWeekBar(CustomWeekBar.class);

        binding.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                binding.tvLunar.setVisibility(View.VISIBLE);
                binding.tvYear.setVisibility(View.VISIBLE);
                binding.tvMonthDay.setText(getString(R.string.date_month_day, calendar.getMonth()+"", calendar.getDay()+""));
                binding.tvYear.setText(String.valueOf(calendar.getYear()));
                binding.tvLunar.setText(calendar.getLunar());
                mYear = calendar.getYear();
                selectDay = getString(R.string.date_year_month_day, calendar.getYear() + "", calendar.getMonth() + "", calendar.getDay() + "");
                getEventData();

            }
        });
        binding.calendarView.setOnYearChangeListener(year -> {
            binding.tvMonthDay.setText(String.valueOf(year));
        });

        binding.flCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.calendarView.scrollToCurrent();

            }
        });

        binding.tvMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.calendarLayout.isExpand()) {
                    binding.calendarLayout.expand();
                    return;
                }
                binding.calendarView.showYearSelectLayout(mYear);
                binding.tvLunar.setVisibility(View.GONE);
                binding.tvYear.setVisibility(View.GONE);
                binding.tvMonthDay.setText(String.valueOf(mYear));
            }
        });
    }

    private void initView() {
        binding.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarActivity.this, AddEventDataActivity.class));
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyListRecyclerAdapter(mList, this);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
                PopMenu.show(new String[]{"编辑", "删除"})
                        .setOnMenuItemClickListener(new OnMenuItemClickListener<PopMenu>() {
                            @Override
                            public boolean onClick(PopMenu dialog, CharSequence text, int index) {
                                if (index == 0) {
                                    Intent intent = new Intent(CalendarActivity.this, AddEventDataActivity.class);
                                    intent.putExtra("bean", mList.get(position));
                                    intent.putExtra("edit", true);
                                    startActivity(intent);
                                } else {
                                    MessageDialog.show("提示", "确定要删除此条信息吗？", "确定")
                                            .setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
                                                @Override
                                                public boolean onClick(MessageDialog baseDialog, View v) {
                                                    if (mList.get(position).getCalendarEvent() > 0) {
                                                        CalendarProviderManager.deleteCalendarEvent(CalendarActivity.this, mList.get(position).getCalendarEvent());
                                                    }
                                                    LitePal.delete(EventDataBean.class, mList.get(position).getId());
                                                    onStart();
                                                    return false;
                                                }
                                            });

                                }
                                return false;
                            }
                        })
                        .setOnIconChangeCallBack(new OnIconChangeCallBack<PopMenu>(true) {
                            @Override
                            public int getIcon(PopMenu dialog, int index, String menuText) {
                                switch (menuText) {
                                    case "编辑":
                                        return R.mipmap.img_dialogx_demo_edit;
                                    case "删除":
                                        return R.mipmap.img_dialogx_demo_delete;
                                    default:
                                        return 0;
                                }
                            }
                        });
            }
        });


    }
    private void loadData() {
        try {
            List<EventDataBean> list = LitePal.where("userId = ?",userId+"").find(EventDataBean.class);
            if (null != list) {
                Map<String, Calendar> map = new HashMap<>();
                for (EventDataBean record : list) {
                    String dateTag = DateUtils.getFormatyyyyMMdd(record.getTime());
                    String[] strings = dateTag.split("-");
                    int year = Integer.parseInt(strings[0]);
                    int month = Integer.parseInt(strings[1]);
                    int day = Integer.parseInt(strings[2]);
                    map.put(getSchemeCalendar(year, month, day, 0xFFCC0000, "记").toString(),
                            getSchemeCalendar(year, month, day, 0xFFCC0000, "记"));
                }
                //此方法在巨大的数据量上不影响遍历性能，推荐使用
                binding.calendarView.setSchemeDate(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
//        calendar.addScheme(0xFF008800, text);
        return calendar;
    }


}