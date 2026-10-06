package com.elearning.serviceimpl;

import com.elearning.entity.Course;
import com.elearning.entity.User;
import com.elearning.repository.CourseRepository;
import com.elearning.repository.UserRepository;
import com.elearning.service.CourseService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(
            CourseRepository courseRepository,
            UserRepository userRepository) {

        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Course createCourse(
            String title,
            String description,
            Double price,
            boolean visible,
            String trainerUsername) {

        User trainer = userRepository
                .findByUsername(trainerUsername)
                .orElseThrow(() ->
                        new RuntimeException("Trainer not found"));

        Course course = new Course();

        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price);
        course.setVisible(visible);
        course.setTrainer(trainer);

        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getVisibleCourses() {
        return courseRepository.findByVisibleTrue();
    }

    @Override
    public List<Course> getTrainerCourses(String trainerUsername) {

        User trainer = userRepository
                .findByUsername(trainerUsername)
                .orElseThrow(() ->
                        new RuntimeException("Trainer not found"));

        return courseRepository.findByTrainerId(trainer.getId());
    }
    
    @Override
    public Course updateCourseVisibility(Long courseId, boolean visible) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        course.setVisible(visible);

        return courseRepository.save(course);
    }
    
    @Override
    public Course updateCourse(
            Long courseId,
            String title,
            String description,
            Double price,
            Boolean visible,
            String trainerUsername) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        User trainer = userRepository
                .findByUsername(trainerUsername)
                .orElseThrow(() ->
                        new RuntimeException("Trainer not found"));

        if (!course.getTrainer().getId()
                .equals(trainer.getId())) {

            throw new RuntimeException(
                    "You are not the owner of this course"
            );
        }

        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price);
        course.setVisible(visible);

        return courseRepository.save(course);
    }
}