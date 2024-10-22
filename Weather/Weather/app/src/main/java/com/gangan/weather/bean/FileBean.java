package com.gangan.weather.bean;

import java.io.Serializable;

public class FileBean implements Serializable {
   private String name;//文件名称
   private String path;//文件路径
   private long size ;//文件时长

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPath() {
      return path;
   }

   public void setPath(String path) {
      this.path = path;
   }


   public long getSize() {
      return size;
   }

   public void setSize(long size) {
      this.size = size;
   }
}
