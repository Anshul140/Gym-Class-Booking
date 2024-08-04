package com.phonpe.mc.service;

import com.phonpe.mc.model.User;

public interface UserService {

  void register(User user);
  User login(String email, String password);
  void bookClass(String userId, String classId);
  void joinWaitlist(String userId, String classId);
  void cancelClassBooking(String userId, String classId);
}
