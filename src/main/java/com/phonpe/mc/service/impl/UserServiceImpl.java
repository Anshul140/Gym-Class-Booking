package com.phonpe.mc.service.impl;

import com.phonpe.mc.constants.UserTierConfig;
import com.phonpe.mc.enums.UserTier;
import com.phonpe.mc.model.User;
import com.phonpe.mc.model.Class;
import com.phonpe.mc.repository.ClassRepository;
import com.phonpe.mc.repository.UserRepository;
import com.phonpe.mc.service.UserService;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserServiceImpl implements UserService {

  private final Lock bookingLock = new ReentrantLock();
  private UserRepository userRepository;
  private ClassRepository classRepository;

  public UserServiceImpl(UserRepository userRepository, ClassRepository classRepository) {
    this.userRepository = userRepository;
    this.classRepository = classRepository;
  }

  @Override
  public void register(User user) {
    if (userRepository.emailExists(user.getEmail())) {
      throw new IllegalArgumentException("Email already exists.");
    }
    user.setId(String.valueOf(UUID.randomUUID()));
    userRepository.addUser(user);
  }

  @Override
  public User login(String email, String password) {
    User user = userRepository.getUserByEmail(email);
    if (user != null && user.getPassword().equals(password)) {
      return user;
    }
    return null;
  }

  @Override
  public void bookClass(String userId, String classId) {
    bookingLock.lock();
    try {
      User user = userRepository.getUserById(userId);
      Class gymClass = classRepository.getClassById(classId);

      if (user == null || gymClass == null) {
        throw new IllegalArgumentException("User or Class not found.");
      }

      // Check if class is full
      if (gymClass.getCurrentlyEnrolled().size() >= gymClass.getMaxCapacity()) {
        throw new IllegalArgumentException("Class is already full.");
      }

      // Check if user has reached their booking limit
      int maxClassesAllowed = getMaxClassesAllowed(user.getUserTier());
      if (user.getBookedClasses() >= maxClassesAllowed) {
        throw new IllegalArgumentException("Booking limit reached for your user tier.");
      }

      // Add user to the class
      gymClass.getCurrentlyEnrolled().add(user);
      user.setBookedClasses(user.getBookedClasses() + 1);

      // Update repositories
      classRepository.updateClass(gymClass);
      userRepository.updateUser(user);

    } finally {
      bookingLock.unlock();
    }
  }

  @Override
  public void joinWaitlist(String userId, String classId) {
    User user = userRepository.getUserById(userId);
    Class gymClass = classRepository.getClassById(classId);

    if (user == null) {
      throw new IllegalArgumentException("User not found.");
    }
    if (gymClass == null) {
      throw new IllegalArgumentException("Class not found.");
    }
    if (gymClass.getCurrentlyEnrolled().contains(user)) {
      throw new IllegalArgumentException("User is already enrolled in this class.");
    }
    if (gymClass.getWaitingUsers().contains(user)) {
      throw new IllegalArgumentException("User is already on the waitlist.");
    }

    gymClass.getWaitingUsers().add(user);
    classRepository.updateClass(gymClass);
  }

  @Override
  public void cancelClassBooking(String userId, String classId) {
    User user = userRepository.getUserById(userId);
    Class gymClass = classRepository.getClassById(classId);

    if (gymClass == null) {
      throw new IllegalArgumentException("Class not found.");
    }

    if (user == null) {
      throw new IllegalArgumentException("User not found.");
    }

    // Check if the user is currently enrolled in the class
    if (!gymClass.getCurrentlyEnrolled().contains(user)) {
      throw new IllegalArgumentException("User is not enrolled in this class.");
    }


    if (gymClass != null && user != null) {
      gymClass.getCurrentlyEnrolled().remove(user);
      user.setBookedClasses(user.getBookedClasses() - 1);

      // Check if there are waitlisted users to add
      if (!gymClass.getWaitingUsers().isEmpty()) {
        User nextUser = gymClass.getWaitingUsers().remove(0);
        gymClass.getCurrentlyEnrolled().add(nextUser);
        nextUser.setBookedClasses(nextUser.getBookedClasses() + 1);

        // Update repositories
        userRepository.updateUser(nextUser);
      }

      // Update repositories
      classRepository.updateClass(gymClass);
      userRepository.updateUser(user);
    }
  }

  private int getMaxClassesAllowed(UserTier userTier) {
    return switch (userTier) {
      case PLATINUM -> UserTierConfig.PLATINUM;
      case GOLD -> UserTierConfig.GOLD;
      case SILVER -> UserTierConfig.SILVER;
      default -> 0;
    };
  }
}
