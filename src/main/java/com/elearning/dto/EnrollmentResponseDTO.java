package com.elearning.dto;

import java.time.LocalDateTime;

public class EnrollmentResponseDTO {

    private Long id;
    private Long courseId;
    private String courseTitle;
    private String studentUsername;
    private LocalDateTime enrolledAt;
    private String status;

    public EnrollmentResponseDTO() {
    }

    public EnrollmentResponseDTO(
            Long id,
            Long courseId,
            String courseTitle,
            String studentUsername,
            LocalDateTime enrolledAt,
            String status) {

        this.id = id;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.studentUsername = studentUsername;
        this.enrolledAt = enrolledAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public String getStatus() {
        return status;
    }
}