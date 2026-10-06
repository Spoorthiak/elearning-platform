package com.elearning.dto;

public class LessonResponseDTO {

    private Long id;
    private String title;
    private String content;
    private Integer lessonOrder;
    private String videoUrl;
    private Long courseId;

    public LessonResponseDTO() {
    }

    public LessonResponseDTO(
            Long id,
            String title,
            String content,
            Integer lessonOrder,
            String videoUrl,
            Long courseId
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.lessonOrder = lessonOrder;
        this.videoUrl = videoUrl;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(Integer lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}