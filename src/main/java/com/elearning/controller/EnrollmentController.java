package com.elearning.controller;

import com.elearning.dto.EnrollmentResponseDTO;
import com.elearning.entity.Enrollment;
import com.elearning.service.EnrollmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(
            EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<?> enrollStudent(
            @PathVariable Long courseId,
            Authentication authentication) {

        try {

            Enrollment enrollment =
                    enrollmentService.enrollStudent(
                            courseId,
                            authentication.getName()
                    );

            return ResponseEntity.ok(toDTO(enrollment));

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @GetMapping("/my-enrollments")
    public ResponseEntity<List<EnrollmentResponseDTO>>
            getMyEnrollments(
                    Authentication authentication) {

        List<EnrollmentResponseDTO> enrollments =
                enrollmentService
                        .getMyEnrollments(
                                authentication.getName()
                        )
                        .stream()
                        .map(this::toDTO)
                        .toList();

        return ResponseEntity.ok(enrollments);
    }

    private EnrollmentResponseDTO toDTO(
            Enrollment enrollment) {

        return new EnrollmentResponseDTO(
                enrollment.getId(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getTitle(),
                enrollment.getStudent().getUsername(),
                enrollment.getEnrolledAt(),
                enrollment.getStatus()
        );
    }
}