package com.elearning.controller;

import com.elearning.entity.Course;
import com.elearning.entity.User;
import com.elearning.service.CourseService;
import com.elearning.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;

    public AdminController(
            UserService userService,
            CourseService courseService) {

        this.userService = userService;
        this.courseService = courseService;
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }
   
    @PutMapping("/users/{userId}/role")
    public ResponseEntity<User> updateUserRole(
            @PathVariable Long userId,
            @RequestParam String role) {

        return ResponseEntity.ok(
                userService.updateUserRole(userId, role)
        );
    }
    
    @PutMapping("/courses/{courseId}/visibility")
    public ResponseEntity<Course> updateCourseVisibility(
            @PathVariable Long courseId,
            @RequestParam boolean visible) {

        return ResponseEntity.ok(
                courseService.updateCourseVisibility(
                        courseId,
                        visible
                )
        );
    }
    
    @PostMapping("/trainers")
    public ResponseEntity<User> createTrainer(
            @RequestParam String username,
            @RequestParam String password) {

        return ResponseEntity.ok(
                userService.createTrainer(username, password)
        );
    }
    
    
    
}