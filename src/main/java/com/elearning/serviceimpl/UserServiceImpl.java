package com.elearning.serviceimpl;

import com.elearning.entity.User;
import com.elearning.repository.UserRepository;
import com.elearning.service.UserService;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String username, String password) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = new User();

        user.setUsername(username);

        user.setPassword(
                passwordEncoder.encode(password)
        );

        user.setRole("ROLE_STUDENT");

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {

        return userRepository
                .findByUsername(username)
                .orElse(null);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public User updateUserRole(Long userId, String role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!role.equals("ROLE_STUDENT") &&
            !role.equals("ROLE_TRAINER")) {

            throw new RuntimeException(
                    "Invalid role. Use ROLE_STUDENT or ROLE_TRAINER"
            );
        }

        user.setRole(role);

        return userRepository.save(user);
    }
    
    @Override
    public User createTrainer(String username, String password) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = new User();

        user.setUsername(username);

        user.setPassword(
                passwordEncoder.encode(password)
        );

        user.setRole("ROLE_TRAINER");

        return userRepository.save(user);
    }
}