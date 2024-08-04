package com.phonpe.mc.service;
import com.phonpe.mc.model.Class;

public interface AdminService {

  void createClass(Class newClass);
  void cancelClass(String classId);
}
