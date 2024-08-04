package com.phonpe.mc.repository;

import com.phonpe.mc.model.User;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

  private Map<String, User> users = new HashMap<>();

  public void addUser(User user) {
    users.put(user.getId(), user);
  }

  public User getUserById(String id) {
    return users.get(id);
  }

  public User getUserByEmail(String email) {
    for (User user : users.values()) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;
  }

  public boolean emailExists(String email) {
    return getUserByEmail(email) != null;
  }

  public void updateUser(User user) {
    if (user != null && users.containsKey(user.getId())) {
      users.put(user.getId(), user);
    } else {
      throw new IllegalArgumentException("User not found.");
    }
  }
}
