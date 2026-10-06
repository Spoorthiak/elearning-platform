package com.elearning.controller;

import com.elearning.dto.CourseResponseDTO;
import com.elearning.entity.Course;
import com.elearning.service.CourseService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(
            @RequestBody Course course,
            Authentication authentication) {

        Course savedCourse = courseService.createCourse(
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                course.isVisible(),
                authentication.getName()
        );

        return ResponseEntity.ok(toDTO(savedCourse));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {

        List<CourseResponseDTO> courses =
                courseService.getAllCourses()
                        .stream()
                        .map(this::toDTO)
                        .toList();

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/visible")
    public ResponseEntity<List<CourseResponseDTO>> getVisibleCourses() {

        List<CourseResponseDTO> courses =
                courseService.getVisibleCourses()
                        .stream()
                        .map(this::toDTO)
                        .toList();

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/my-courses")
    public ResponseEntity<List<CourseResponseDTO>> getMyCourses(
            Authentication authentication) {

        List<CourseResponseDTO> courses =
                courseService.getTrainerCourses(
                        authentication.getName()
                )
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(courses);
    }

    private CourseResponseDTO toDTO(Course course) {

        return new CourseResponseDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                course.isVisible(),
                course.getTrainer().getUsername()
        );
    }
    
    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course,
            Authentication authentication) {

        try {

            Course updatedCourse = courseService.updateCourse(
                    courseId,
                    course.getTitle(),
                    course.getDescription(),
                    course.getPrice(),
                    course.isVisible(),
                    authentication.getName()
            );

            return ResponseEntity.ok(toDTO(updatedCourse));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}