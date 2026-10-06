package com.elearning.service;

import com.elearning.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {

    Enrollment enrollStudent(
            Long courseId,
            String studentUsername
    );

    List<Enrollment> getMyEnrollments(
            String studentUsername
    );
}