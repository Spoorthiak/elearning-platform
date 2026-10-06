package com.elearning.serviceimpl;

import com.elearning.entity.Course;
import com.elearning.entity.Enrollment;
import com.elearning.entity.User;
import com.elearning.repository.CourseRepository;
import com.elearning.repository.EnrollmentRepository;
import com.elearning.repository.UserRepository;
import com.elearning.service.EnrollmentService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            UserRepository userRepository,
            CourseRepository courseRepository) {

        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Enrollment enrollStudent(
            Long courseId,
            String studentUsername) {

        User student = userRepository
                .findByUsername(studentUsername)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Course course = courseRepository
                .findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        if (enrollmentRepository
                .existsByStudentIdAndCourseId(
                        student.getId(),
                        course.getId())) {

            throw new RuntimeException(
                    "Student is already enrolled in this course"
            );
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollment.setStatus("ACTIVE");

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> getMyEnrollments(
            String studentUsername) {

        User student = userRepository
                .findByUsername(studentUsername)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        return enrollmentRepository
                .findByStudentId(student.getId());
    }
}