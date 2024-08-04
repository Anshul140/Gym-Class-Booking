package com.phonpe.mc.repository;

import java.util.HashMap;
import java.util.Map;
import com.phonpe.mc.model.Class;

public class ClassRepository {

  private Map<String, Class> classes = new HashMap<>();

  public void addClass(Class newClass) {
    classes.put(newClass.getId(), newClass);
  }

  public Class getClassById(String id) {
    return classes.get(id);
  }

  public Map<String, Class> getAllClasses() {
    return classes;
  }

  public void updateClass(Class gymClass) {
    if (gymClass != null && classes.containsKey(gymClass.getId())) {
      classes.put(gymClass.getId(), gymClass);
    } else {
      throw new IllegalArgumentException("Class not found.");
    }
  }
}
