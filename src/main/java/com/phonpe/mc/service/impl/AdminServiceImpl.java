package com.phonpe.mc.service.impl;

import com.phonpe.mc.enums.ClassState;
import com.phonpe.mc.model.Class;
import com.phonpe.mc.repository.ClassRepository;
import com.phonpe.mc.service.AdminService;
import java.util.UUID;

public class AdminServiceImpl implements AdminService {
  private ClassRepository classRepository;

  public AdminServiceImpl(ClassRepository classRepository) {
    this.classRepository = classRepository;
  }

  @Override
  public void createClass(Class newClass) {
    newClass.setId(String.valueOf(UUID.randomUUID()));
    classRepository.addClass(newClass);
  }

  @Override
  public void cancelClass(String classId) {
    Class gymClass = classRepository.getClassById(classId);
    if (gymClass != null) {
      gymClass.setState(ClassState.INACTIVE);
    }
  }
}
