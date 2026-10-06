package com.elearning.service;

import com.elearning.entity.Lesson;

import java.util.List;

public interface LessonService {

    Lesson createLesson(
            Long courseId,
            String title,
            String content,
            Integer lessonOrder,
            String videoUrl,
            String trainerUsername
    );

    List<Lesson> getLessonsByCourse(Long courseId);

    Lesson updateLesson(
            Long lessonId,
            String title,
            String content,
            Integer lessonOrder,
            String videoUrl,
            String trainerUsername
    );

    void deleteLesson(
            Long lessonId,
            String trainerUsername
    );
}