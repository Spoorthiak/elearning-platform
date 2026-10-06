package com.elearning.repository;

import com.elearning.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository
        extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseId(
            Long studentId,
            Long courseId
    );

    Optional<Enrollment> findByStudentIdAndCourseId(
            Long studentId,
            Long courseId
    );

    List<Enrollment> findByStudentId(Long studentId);
}