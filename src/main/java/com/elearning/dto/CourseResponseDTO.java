package com.elearning.dto;

public class CourseResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private boolean visible;
    private String trainerUsername;

    public CourseResponseDTO() {
    }

    public CourseResponseDTO(
            Long id,
            String title,
            String description,
            Double price,
            boolean visible,
            String trainerUsername) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.visible = visible;
        this.trainerUsername = trainerUsername;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }
}