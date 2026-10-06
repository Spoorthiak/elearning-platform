package com.elearning.controller;

import com.elearning.dto.CommentResponseDTO;
import com.elearning.entity.Comment;
import com.elearning.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/course/{courseId}")
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long courseId,
            @RequestBody String content,
            Authentication authentication) {

        Comment comment = commentService.addComment(
                courseId,
                content,
                authentication.getName()
        );

        return ResponseEntity.ok(toDTO(comment));
    }

    @PostMapping("/{commentId}/reply")
    public ResponseEntity<CommentResponseDTO> addReply(
            @PathVariable Long commentId,
            @RequestBody String content,
            Authentication authentication) {

        Comment reply = commentService.addReply(
                commentId,
                content,
                authentication.getName()
        );

        return ResponseEntity.ok(toDTO(reply));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CommentResponseDTO>> getCourseComments(
            @PathVariable Long courseId) {

        List<CommentResponseDTO> comments =
                commentService.getCourseComments(courseId)
                        .stream()
                        .map(this::toDTO)
                        .toList();

        return ResponseEntity.ok(comments);
    }

    private CommentResponseDTO toDTO(Comment comment) {

        List<CommentResponseDTO> replies =
                commentService.getReplies(comment.getId())
                        .stream()
                        .map(this::toDTO)
                        .toList();

        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getCreatedAt(),
                replies
        );
    }
}