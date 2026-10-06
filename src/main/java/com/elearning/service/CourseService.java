package com.elearning.service;

import com.elearning.entity.Course;

import java.util.List;

public interface CourseService {

    Course createCourse(
            String title,
            String description,
            Double price,
            boolean visible,
            String trainerUsername
    );

    List<Course> getAllCourses();

    List<Course> getVisibleCourses();

    List<Course> getTrainerCourses(String trainerUsername);

    Course updateCourseVisibility(Long courseId, boolean visible);
    
    Course updateCourse(
            Long courseId,
            String title,
            String description,
            Double price,
            Boolean visible,
            String trainerUsername
    );
}