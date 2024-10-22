package com.gangan.weather;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.gangan.weather.bean.EventDataBean;
import com.gangan.weather.databinding.ActivityAddEventDataBinding;
import com.gangan.weather.utils.Constant;
import com.gangan.weather.utils.DateUtils;
import com.gangan.weather.utils.SPUtils;
import com.kongzue.dialogx.dialogs.PopMenu;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener;
import com.kyle.calendarprovider.calendar.CalendarEvent;
import com.kyle.calendarprovider.calendar.CalendarProviderManager;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;

public class AddEventDataActivity extends BaseActivity {
    private ActivityAddEventDataBinding binding;
    private TimePickerView pvTime;
    private int scheduleType = 0;
    private boolean isEdit = false;
    private EventDataBean mBean;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEventDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isEdit = getIntent().getBooleanExtra("edit", false);
        if (isEdit) {
            mBean = (EventDataBean) getIntent().getSerializableExtra("bean");
            binding.tvType.setText(Constant.scheduleStr[mBean.getClassify()]);
            binding.tvTime.setText(DateUtils.getCurrentTime(mBean.getTime()));
            binding.etContent.setText(mBean.getDescribe());
        } else {
            String time = DateUtils.getCurrentTime(System.currentTimeMillis());
            binding.tvTime.setText(time);
        }

        binding.rlTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pvTime!=null){
                    pvTime.show(binding.tvTime);
                }
            }
        });

        binding.rlType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopMenu.show(view, Constant.scheduleStr)
                        .setOnMenuItemClickListener(new OnMenuItemClickListener<PopMenu>() {
                            @Override
                            public boolean onClick(PopMenu dialog, CharSequence text, int index) {
                                binding.tvType.setText(text);
                                scheduleType = index;
                                return false;
                            }
                        });
            }
        });

        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                TextView btn = (TextView) v;
                btn.setText(DateUtils.getCurrentTime(date.getTime()));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, true})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(16)//字号
                .setDecorView(null)
                .build();

        binding.btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long userId = (long) SPUtils.get(AddEventDataActivity.this, SPUtils.USER_ID, 0L);
                long time = DateUtils.getStringToLong(binding.tvTime.getText().toString());
                content = binding.etContent.getText().toString();
                long eventId=setCalendar(time);
                save(eventId,time,userId);
            }
        });
    }

    private long setCalendar(long time) {
        CalendarEvent calendarEvent = new CalendarEvent(
                Constant.scheduleStr[scheduleType],
                content,
                "",
                time,
                time + 60000,
                0, null
        );
        // 添加事件
        long eventId = CalendarProviderManager.addCalendarEvent(AddEventDataActivity.this, calendarEvent);
        return eventId;
    }

    private void save(long eventId,long time,long userId) {
        if (isEdit) {
            if (mBean.getCalendarEvent() > 0) {
                CalendarProviderManager.deleteCalendarEvent(AddEventDataActivity.this, mBean.getCalendarEvent());
            }
            ContentValues values = new ContentValues();
            values.put("classify", scheduleType);
            values.put("describe", content);
            values.put("calendarEvent", eventId);
            values.put("time", time);
            LitePal.update(EventDataBean.class, values, mBean.getId());
        }else {
            Log.e("sadas", eventId + "");
            EventDataBean bean = new EventDataBean();
            bean.setClassify(scheduleType);
            bean.setDescribe(content);
            bean.setCalendarEvent(eventId);
            bean.setTime(time);
            bean.setUserId(userId);
            bean.save();
        }
        finish();
    }

}