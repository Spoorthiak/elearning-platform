package com.elearning.repository;

import com.elearning.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCourseIdAndParentCommentIsNullOrderByCreatedAtAsc(Long courseId);

    List<Comment> findByParentCommentIdOrderByCreatedAtAsc(Long parentCommentId);
}