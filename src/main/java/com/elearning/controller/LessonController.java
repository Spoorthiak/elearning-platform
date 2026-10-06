package com.elearning.controller;

import com.elearning.dto.LessonResponseDTO;
import com.elearning.entity.Lesson;
import com.elearning.service.LessonService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }


    // =========================
    // CREATE LESSON
    // =========================

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<LessonResponseDTO> createLesson(
            @PathVariable Long courseId,
            @RequestBody Lesson lesson,
            Authentication authentication) {

        Lesson savedLesson = lessonService.createLesson(
                courseId,
                lesson.getTitle(),
                lesson.getContent(),
                lesson.getLessonOrder(),
                lesson.getVideoUrl(),
                authentication.getName()
        );

        return ResponseEntity.ok(
                toDTO(savedLesson)
        );
    }


    // =========================
    // GET LESSONS
    // =========================

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<LessonResponseDTO>> getLessons(
            @PathVariable Long courseId) {

        List<LessonResponseDTO> lessons =
                lessonService
                        .getLessonsByCourse(courseId)
                        .stream()
                        .map(this::toDTO)
                        .toList();

        return ResponseEntity.ok(lessons);
    }


    // =========================
    // CONVERT TO DTO
    // =========================

    private LessonResponseDTO toDTO(Lesson lesson) {

        return new LessonResponseDTO(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getContent(),
                lesson.getLessonOrder(),
                lesson.getVideoUrl(),
                lesson.getCourse().getId()
        );
    }


    // =========================
    // UPDATE LESSON
    // =========================

    @PutMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<LessonResponseDTO> updateLesson(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            @RequestBody Lesson lesson,
            Authentication authentication) {

        Lesson updatedLesson =
                lessonService.updateLesson(
                        lessonId,
                        lesson.getTitle(),
                        lesson.getContent(),
                        lesson.getLessonOrder(),
                        lesson.getVideoUrl(),
                        authentication.getName()
                );

        return ResponseEntity.ok(
                toDTO(updatedLesson)
        );
    }


    // =========================
    // DELETE LESSON
    // =========================

    @DeleteMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<?> deleteLesson(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            Authentication authentication) {

        try {

            lessonService.deleteLesson(
                    lessonId,
                    authentication.getName()
            );

            return ResponseEntity.ok(
                    "Lesson deleted successfully!"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}