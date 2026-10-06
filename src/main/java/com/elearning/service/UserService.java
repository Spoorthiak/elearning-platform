package com.elearning.service;

import java.util.List;

import com.elearning.entity.User;

public interface UserService {

    User registerUser(String username, String password);

    User findByUsername(String username);

    List<User> getAllUsers();

    User updateUserRole(Long userId, String role);

    User createTrainer(String username, String password);
}