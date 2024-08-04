package com.phonpe.mc;

import com.phonpe.mc.enums.ClassState;
import com.phonpe.mc.enums.ClassType;
import com.phonpe.mc.enums.UserTier;
import com.phonpe.mc.enums.UserType;
import com.phonpe.mc.model.User;
import com.phonpe.mc.model.Class;
import com.phonpe.mc.repository.ClassRepository;
import com.phonpe.mc.repository.UserRepository;
import com.phonpe.mc.service.AdminService;
import com.phonpe.mc.service.ClassesService;
import com.phonpe.mc.service.UserService;
import com.phonpe.mc.service.impl.AdminServiceImpl;
import com.phonpe.mc.service.impl.ClassesServiceImpl;
import com.phonpe.mc.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MainApplication {
  private static ClassRepository classRepository = new ClassRepository();
  private static UserRepository userRepository = new UserRepository();
  private static UserService userService = new UserServiceImpl(userRepository, classRepository);
  private static AdminService adminService = new AdminServiceImpl(classRepository);
  private static ClassesService classService = new ClassesServiceImpl(classRepository);


  private static User defaultAdmin = new User();
  private static Class defaultClass = new Class();
  private static User currentUser = null; // To track the logged-in user

  static {
    // Creating a default admin user
    defaultAdmin.setId("admin");
    defaultAdmin.setUserName("admin");
    defaultAdmin.setPassword("admin123");
    defaultAdmin.setEmail("admin@gym.com");
    defaultAdmin.setUserType(UserType.ADMIN);
    defaultClass.setState(ClassState.ACTIVE);
    defaultAdmin.setUserTier(null); // Admins don't have a user tier

    defaultClass.setId("class-1");
    defaultClass.setMaxCapacity(1);
    defaultClass.setStartTime(LocalDateTime.parse("2024-08-05T10:00"));
    defaultClass.setEndTime(LocalDateTime.parse("2024-08-05T11:00"));
    defaultClass.setType(ClassType.GYM);

    // save to repo
    userRepository.addUser(defaultAdmin);
    classRepository.addClass(defaultClass);

  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    while (running) {
      System.out.println("Select an option:");
      System.out.println("1. Signup");
      System.out.println("2. Login");
      System.out.println("3. Book class");
      System.out.println("4. Cancel class");
      System.out.println("5. Admin login");
      System.out.println("6. Admin create class");
      System.out.println("7. Admin cancel class");
      System.out.println("8. Show class details");
      System.out.println("9. Show all classes details");
      System.out.println("10. Join waiting list");
      System.out.println("11. Exit");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          handleSignup(scanner);
          break;
        case 2:
          handleLogin(scanner);
          break;
        case 3:
          handleBookClass(scanner);
          break;
        case 4:
          handleCancelClass(scanner);
          break;
        case 5:
          handleAdminLogin(scanner);
          break;
        case 6:
          handleAdminCreateClass(scanner);
          break;
        case 7:
          handleAdminCancelClass(scanner);
          break;
        case 8:
          handleShowClassDetails(scanner);
          break;
        case 9:
          handleShowAllClassesDetails();
          break;
        case 10:
          joinWaitingList(scanner);
          break;
        case 11:
          running = false;
          break;
        default:
          System.out.println("Invalid choice, please try again.");
      }
    }

    scanner.close();
  }

  private static void joinWaitingList(Scanner scanner) {
    String userId = currentUser.getId();
    UserType userType = currentUser.getUserType();

    if(userType.equals(UserType.ADMIN)) {
      System.out.println("Admin cannot join waiting list! Please login as customer");
      return;
    }

    System.out.println("Enter class ID:");
    String classId = scanner.nextLine();
    try {
      userService.joinWaitlist(userId, classId);
      System.out.println("Successfully joined the waiting list.");
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static void handleShowAllClassesDetails() {
    classService.showAllClassesDetails();
  }

  private static void handleSignup(Scanner scanner) {
    System.out.println("Enter username:");
    String userName = scanner.nextLine();
    System.out.println("Enter password:");
    String password = scanner.nextLine();
    System.out.println("Enter email:");
    String email = scanner.nextLine();
    System.out.println("Select user tier (PLATINUM, GOLD, SILVER):");
    String tier = scanner.nextLine();

    User user = new User();
    user.setUserName(userName);
    user.setPassword(password);
    user.setEmail(email);
    user.setUserType(UserType.CUSTOMER);
    user.setUserTier(UserTier.valueOf(tier.toUpperCase()));

    try {
      userService.register(user);
      currentUser = user;
      System.out.println("User registered successfully.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void handleLogin(Scanner scanner) {
    System.out.println("Enter email:");
    String email = scanner.nextLine();
    System.out.println("Enter password:");
    String password = scanner.nextLine();

    User user = userService.login(email, password);
    if (user != null) {
      currentUser = user;
      System.out.println("Login successful. User ID: " + user.getId());
    } else {
      System.out.println("Invalid email or password.");
    }
  }

  private static void handleBookClass(Scanner scanner) {
    if (currentUser == null || currentUser.getUserType() != UserType.CUSTOMER) {
      System.out.println("You must be logged in as a customer to book a class.");
      return;
    }

    System.out.println("Enter the Class ID to book:");
    String classId = scanner.nextLine();

    try {
      userService.bookClass(currentUser.getId(), classId);
      System.out.println("Class booked successfully.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error occurred while booking! " + e.getMessage());
    }
  }

  private static void handleCancelClass(Scanner scanner) {
    if (currentUser == null || currentUser.getUserType() != UserType.CUSTOMER) {
      System.out.println("You must be logged in as a customer to cancel a class.");
      return;
    }

    System.out.println("Enter the Class ID to cancel:");
    String classId = scanner.nextLine();

    try {
      userService.cancelClassBooking(currentUser.getId(), classId);
      System.out.println("Class booking cancelled successfully.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error occurred while cancelling! " + e.getMessage());
    }
  }

  private static void handleAdminLogin(Scanner scanner) {
    System.out.println("Enter admin email:");
    String email = scanner.nextLine();
    System.out.println("Enter admin password:");
    String password = scanner.nextLine();

    if (defaultAdmin.getEmail().equals(email) && defaultAdmin.getPassword().equals(password)) {
      currentUser = defaultAdmin;
      System.out.println("Admin login successful.");
    } else {
      System.out.println("Invalid admin email or password.");
    }
  }

  private static void handleAdminCreateClass(Scanner scanner) {
    if (currentUser == null || currentUser.getUserType() != UserType.ADMIN) {
      System.out.println("You must be logged in as an admin to create a class.");
      return;
    }

    System.out.println("Enter class type (GYM, YOGA, DANCE):");
    String type = scanner.nextLine();
    System.out.println("Enter max capacity:");
    int maxCapacity = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    System.out.println("Enter start time (YYYY-MM-DDTHH:MM):");
    String startTime = scanner.nextLine();
    System.out.println("Enter end time (YYYY-MM-DDTHH:MM):");
    String endTime = scanner.nextLine();

    Class newClass = new Class();
    newClass.setType(ClassType.valueOf(type.toUpperCase()));
    newClass.setMaxCapacity(maxCapacity);
    newClass.setStartTime(LocalDateTime.parse(startTime));
    newClass.setEndTime(LocalDateTime.parse(endTime));
    newClass.setState(ClassState.ACTIVE);

    adminService.createClass(newClass);
    System.out.println("Class created successfully.");
  }

  private static void handleAdminCancelClass(Scanner scanner) {
    if (currentUser == null || currentUser.getUserType() != UserType.ADMIN) {
      System.out.println("You must be logged in as an admin to cancel a class.");
      return;
    }
    System.out.println("Enter the Class ID to cancel:");
    String classId = scanner.nextLine();

    adminService.cancelClass(classId);
    System.out.println("Class cancelled successfully.");
  }

  private static void handleShowClassDetails(Scanner scanner) {
    System.out.println("Enter the Class ID to view details:");
    String classId = scanner.nextLine();

    classService.showClassDetails(classId);
  }
}
