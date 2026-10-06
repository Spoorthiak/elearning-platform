package com.elearning.controller;

import com.elearning.dto.ProgressResponseDTO;
import com.elearning.entity.Progress;
import com.elearning.service.ProgressService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/lessons/{lessonId}/complete")
    public ResponseEntity<ProgressResponseDTO> markLessonCompleted(
            @PathVariable Long lessonId,
            Authentication authentication) {

        Progress progress = progressService.markLessonCompleted(
                lessonId,
                authentication.getName()
        );

        return ResponseEntity.ok(toDTO(progress));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<List<ProgressResponseDTO>> getCourseProgress(
            @PathVariable Long courseId,
            Authentication authentication) {

        List<ProgressResponseDTO> progressList =
                progressService.getCourseProgress(
                        courseId,
                        authentication.getName()
                )
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(progressList);
    }

    private ProgressResponseDTO toDTO(Progress progress) {

        return new ProgressResponseDTO(
                progress.getId(),
                progress.getCourse().getId(),
                progress.getLesson().getId(),
                progress.getLesson().getTitle(),
                progress.isCompleted(),
                progress.getCompletedAt()
        );
    }
}