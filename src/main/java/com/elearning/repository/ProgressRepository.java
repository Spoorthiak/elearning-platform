package com.elearning.repository;

import com.elearning.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    Optional<Progress> findByStudentIdAndLessonId(Long studentId, Long lessonId);

    List<Progress> findByStudentIdAndCourseId(Long studentId, Long courseId);
    
    List<Progress> findByLessonId(Long lessonId);
}