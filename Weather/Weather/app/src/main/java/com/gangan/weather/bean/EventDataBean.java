package com.gangan.weather.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class EventDataBean extends LitePalSupport implements Serializable {

   private long id;
   private long userId;
   private int classify;//分类，0，其他日程，1学习日程、2工作日程、3家庭日程、4游玩日程
   private String describe;//描述、备注
   private long time;//时间
   private long calendarEvent;//提醒事项ID

   public EventDataBean() {
   }

   public long getUserId() {
      return userId;
   }

   public void setUserId(long userId) {
      this.userId = userId;
   }


   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public int getClassify() {
      return classify;
   }

   public void setClassify(int classify) {
      this.classify = classify;
   }

   public String getDescribe() {
      return describe;
   }

   public void setDescribe(String describe) {
      this.describe = describe;
   }

   public long getTime() {
      return time;
   }

   public void setTime(long time) {
      this.time = time;
   }

   public long getCalendarEvent() {
      return calendarEvent;
   }

   public void setCalendarEvent(long calendarEvent) {
      this.calendarEvent = calendarEvent;
   }
}
