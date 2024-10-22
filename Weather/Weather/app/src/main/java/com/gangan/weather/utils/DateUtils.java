package com.gangan.weather.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

    private static String[] week = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
    /**
     * 获取星期
     * @param date
     * @return
     */
    public static String getDayOfWeek(String date) {
        String[] dates = date.split("-");
        int year=Integer.parseInt(dates[0]);
        int month =Integer.parseInt(dates[1]);
        int day =Integer.parseInt(dates[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1); // 注意：月份是从0开始的，即0表示1月
        calendar.set(Calendar.DAY_OF_MONTH, day);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return week[dayOfWeek-1]; // 返回的是1~7的数字，1代表星期日，7代表星期六
    }

    /**
     * 格式化秒
     * @param seconds
     * @return
     */
    public static String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * 格式化时间
     * @param dates
     * @return
     */
    public static String getFormatHHmmss(long dates) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = sDateFormat.format(new Date(dates));
        return date;
    }
    /**
     * 格式化时间
     * @param dates
     * @return
     */
    public static String getFormatyyyyMMdd(long dates) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date(dates));
        return date;
    }

    /**
     * 字符串时间转long
     * @param dates
     * @return
     */
    public static long getStringToLong(String dates) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(dates);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long timestamp = date.getTime();
        return timestamp;
    }
    public static String getCurrentTime(long dates) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date(dates));
        return date;
    }
}
