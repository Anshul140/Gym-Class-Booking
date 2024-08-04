package com.phonpe.mc.model;

import com.phonpe.mc.enums.ClassState;
import com.phonpe.mc.enums.ClassType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Class {

  private String id;
  private ClassType type;
  private int maxCapacity;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private List<User> currentlyEnrolled = new ArrayList<>();
  private List<User> waitingUsers = new ArrayList<>();
  private ClassState state;

  public Class(){

  }

  public Class(String id, ClassType type, int maxCapacity, LocalDateTime startTime,
      LocalDateTime endTime, ClassState state) {
    this.id = id;
    this.type = type;
    this.maxCapacity = maxCapacity;
    this.startTime = startTime;
    this.endTime = endTime;
    this.state = state;
    this.currentlyEnrolled = new ArrayList<>();
    this.waitingUsers = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ClassType getType() {
    return type;
  }

  public void setType(ClassType type) {
    this.type = type;
  }

  public int getMaxCapacity() {
    return maxCapacity;
  }

  public void setMaxCapacity(int maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public List<User> getCurrentlyEnrolled() {
    return currentlyEnrolled;
  }

  public void setCurrentlyEnrolled(List<User> currentlyEnrolled) {
    this.currentlyEnrolled = currentlyEnrolled;
  }

  public ClassState getState() {
    return state;
  }

  public void setState(ClassState state) {
    this.state = state;
  }

  public List<User> getWaitingUsers() {
    return waitingUsers;
  }

  public void setWaitingUsers(List<User> waitingUsers) {
    this.waitingUsers = waitingUsers;
  }
}
