package com.gangan.weather.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class UserBean extends LitePalSupport implements Serializable {
   private long id;
   private String username;
   private String phone;
   private String password;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
