package com.phonpe.mc.service.impl;

import com.phonpe.mc.model.Class;
import com.phonpe.mc.model.User;
import com.phonpe.mc.repository.ClassRepository;
import com.phonpe.mc.service.ClassesService;
import java.util.ArrayList;
import java.util.List;

public class ClassesServiceImpl implements ClassesService {
  private ClassRepository classRepository;

  public ClassesServiceImpl(ClassRepository classRepository) {
    this.classRepository = classRepository;
  }

  @Override
  public List<Class> showAllClasses() {
    return new ArrayList<>(classRepository.getAllClasses().values());
  }

  @Override
  public void showClassDetails(String classId) {
    Class gymClass = classRepository.getClassById(classId);
    if (gymClass != null) {
      System.out.println("Class ID: " + gymClass.getId());
      System.out.println("Type: " + gymClass.getType());
      System.out.println("Max Capacity: " + gymClass.getMaxCapacity());
      System.out.println("Start Time: " + gymClass.getStartTime());
      System.out.println("End Time: " + gymClass.getEndTime());
      System.out.println("State: " + gymClass.getState());
      System.out.println("Currently Enrolled:");

      if(!gymClass.getCurrentlyEnrolled().isEmpty()) {
        for (User user : gymClass.getCurrentlyEnrolled()) {
          System.out.println(" - " + user.getUserName());
        }
      } else {
        System.out.println("No current enrolled");
      }
      System.out.println("Waitlist:");
      if(!gymClass.getWaitingUsers().isEmpty()){
        for (User user : gymClass.getWaitingUsers()) {
          System.out.println(" - " + user.getUserName());
        }
      } else {
        System.out.println("No waiting users");
      }
    } else {
      System.out.println("Class not found.");
    }
  }

  @Override
  public void showAllClassesDetails() {
    List<Class> classes = showAllClasses();
    if (classes.isEmpty()) {
      System.out.println("No classes available.");
    } else {
      for (Class gymClass : classes) {
        showClassDetails(gymClass.getId());
        System.out.println();
      }
    }
  }
}
