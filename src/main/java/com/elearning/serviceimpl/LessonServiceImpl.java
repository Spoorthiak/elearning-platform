package com.elearning.serviceimpl;

import com.elearning.entity.Course;
import com.elearning.entity.Lesson;
import com.elearning.entity.Progress;
import com.elearning.entity.User;
import com.elearning.repository.CourseRepository;
import com.elearning.repository.LessonRepository;
import com.elearning.repository.ProgressRepository;
import com.elearning.repository.UserRepository;
import com.elearning.service.LessonService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;

    public LessonServiceImpl(
            LessonRepository lessonRepository,
            CourseRepository courseRepository,
            UserRepository userRepository,
            ProgressRepository progressRepository) {

        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
    }

    @Override
    public Lesson createLesson(
            Long courseId,
            String title,
            String content,
            Integer lessonOrder,
            String videoUrl,
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

        Lesson lesson = new Lesson();

        lesson.setTitle(title);
        lesson.setContent(content);
        lesson.setLessonOrder(lessonOrder);
        lesson.setVideoUrl(videoUrl);
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> getLessonsByCourse(Long courseId) {

        return lessonRepository
                .findByCourseIdOrderByLessonOrderAsc(courseId);
    }

    @Override
    public Lesson updateLesson(
            Long lessonId,
            String title,
            String content,
            Integer lessonOrder,
            String videoUrl,
            String trainerUsername) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new RuntimeException("Lesson not found"));

        User trainer = userRepository
                .findByUsername(trainerUsername)
                .orElseThrow(() ->
                        new RuntimeException("Trainer not found"));

        if (!lesson.getCourse().getTrainer().getId()
                .equals(trainer.getId())) {

            throw new RuntimeException(
                    "You are not the owner of this course"
            );
        }

        lesson.setTitle(title);
        lesson.setContent(content);
        lesson.setLessonOrder(lessonOrder);
        lesson.setVideoUrl(videoUrl);

        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(
            Long lessonId,
            String trainerUsername) {

        Lesson lesson = lessonRepository
                .findById(lessonId)
                .orElseThrow(() ->
                        new RuntimeException("Lesson not found"));

        User trainer = userRepository
                .findByUsername(trainerUsername)
                .orElseThrow(() ->
                        new RuntimeException("Trainer not found"));

        if (!lesson.getCourse().getTrainer().getId()
                .equals(trainer.getId())) {

            throw new RuntimeException(
                    "You are not the owner of this course"
            );
        }

        // Delete progress records linked to this lesson
        List<Progress> progressList =
                progressRepository.findByLessonId(lessonId);

        progressRepository.deleteAll(progressList);

        // Delete the lesson
        lessonRepository.delete(lesson);
    }
}