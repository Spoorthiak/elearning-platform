package com.elearning.repository;

import com.elearning.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByVisibleTrue();

    List<Course> findByTrainerId(Long trainerId);
}