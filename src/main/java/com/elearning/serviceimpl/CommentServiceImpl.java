package com.elearning.serviceimpl;

import com.elearning.entity.Comment;
import com.elearning.entity.Course;
import com.elearning.entity.User;
import com.elearning.repository.CommentRepository;
import com.elearning.repository.CourseRepository;
import com.elearning.repository.UserRepository;
import com.elearning.service.CommentService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CourseRepository courseRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment addComment(Long courseId, String content, String username) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();

        comment.setContent(content);
        comment.setUser(user);
        comment.setCourse(course);
        comment.setParentComment(null);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Override
    public Comment addReply(Long commentId, String content, String username) {

        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment reply = new Comment();

        reply.setContent(content);
        reply.setUser(user);
        reply.setCourse(parentComment.getCourse());
        reply.setParentComment(parentComment);
        reply.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(reply);
    }

    @Override
    public List<Comment> getCourseComments(Long courseId) {

        courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return commentRepository
                .findByCourseIdAndParentCommentIsNullOrderByCreatedAtAsc(courseId);
    }
    
    @Override
    public List<Comment> getReplies(Long commentId) {

        commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        return commentRepository
                .findByParentCommentIdOrderByCreatedAtAsc(commentId);
    }
}