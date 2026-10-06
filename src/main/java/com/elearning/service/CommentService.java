package com.elearning.service;

import com.elearning.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(Long courseId, String content, String username);

    Comment addReply(Long commentId, String content, String username);

    List<Comment> getCourseComments(Long courseId);

    List<Comment> getReplies(Long commentId);
}