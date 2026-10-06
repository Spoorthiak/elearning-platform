package com.elearning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTestController {

    @GetMapping("/api/student-test")
    public String studentTest() {
        return "Student access granted!";
    }

    @GetMapping("/api/trainer-test")
    public String trainerTest() {
        return "Trainer access granted!";
    }

    @GetMapping("/api/admin-test")
    public String adminTest() {
        return "Admin access granted!";
    }
}