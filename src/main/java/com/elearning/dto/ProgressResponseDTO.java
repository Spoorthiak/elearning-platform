package com.elearning.dto;

import java.time.LocalDateTime;

public class ProgressResponseDTO {

    private Long id;
    private Long courseId;
    private Long lessonId;
    private String lessonTitle;
    private boolean completed;
    private LocalDateTime completedAt;

    public ProgressResponseDTO() {
    }

    public ProgressResponseDTO(Long id, Long courseId, Long lessonId,
                               String lessonTitle, boolean completed,
                               LocalDateTime completedAt) {
        this.id = id;
        this.courseId = courseId;
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.completed = completed;
        this.completedAt = completedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}