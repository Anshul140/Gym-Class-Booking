package com.phonpe.mc.model;

import com.phonpe.mc.enums.UserTier;
import com.phonpe.mc.enums.UserType;

public class User {

  private String id;
  private String userName;
  private String email;
  private String password;
  private UserTier userTier;
  private UserType userType;
  private int bookedClasses;

  public int getBookedClasses() {
    return bookedClasses;
  }

  public void setBookedClasses(int bookedClasses) {
    this.bookedClasses = bookedClasses;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String username) {
    this.userName = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserTier getUserTier() {
    return userTier;
  }

  public void setUserTier(UserTier userTier) {
    this.userTier = userTier;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public User(String id, String username, String email, String password, UserTier userTier, UserType userType) {
    this.id = id;
    this.userName = username;
    this.password = password;
    this.userTier = userTier;
    this.userType = userType;
    this.email = email;
  }

  public User(){

  }
}
