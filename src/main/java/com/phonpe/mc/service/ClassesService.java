package com.phonpe.mc.service;
import com.phonpe.mc.model.Class;
import java.util.List;


public interface ClassesService {
  List<Class> showAllClasses();
  void showClassDetails(String id);
  void showAllClassesDetails();
}
