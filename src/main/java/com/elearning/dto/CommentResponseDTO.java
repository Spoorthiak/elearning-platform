package com.elearning.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponseDTO {

    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private List<CommentResponseDTO> replies;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, String content, String username,
                              LocalDateTime createdAt,
                              List<CommentResponseDTO> replies) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.replies = replies;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<CommentResponseDTO> getReplies() {
        return replies;
    }
}