package com.elearning.serviceimpl;

import com.elearning.entity.Course;
import com.elearning.entity.Lesson;
import com.elearning.entity.Progress;
import com.elearning.entity.User;
import com.elearning.repository.CourseRepository;
import com.elearning.repository.LessonRepository;
import com.elearning.repository.ProgressRepository;
import com.elearning.repository.UserRepository;
import com.elearning.service.ProgressService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    

    public ProgressServiceImpl(ProgressRepository progressRepository,
                               UserRepository userRepository,
                               LessonRepository lessonRepository,
                               CourseRepository courseRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Progress markLessonCompleted(Long lessonId, String studentUsername) {

        User student = userRepository.findByUsername(studentUsername)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Course course = lesson.getCourse();

        Progress progress = progressRepository
                .findByStudentIdAndLessonId(student.getId(), lessonId)
                .orElse(null);

        if (progress == null) {
            progress = new Progress();
            progress.setStudent(student);
            progress.setCourse(course);
            progress.setLesson(lesson);
        }

        progress.setCompleted(true);
        progress.setCompletedAt(LocalDateTime.now());

        return progressRepository.save(progress);
    }

    @Override
    public List<Progress> getCourseProgress(Long courseId, String studentUsername) {

        User student = userRepository.findByUsername(studentUsername)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return progressRepository
                .findByStudentIdAndCourseId(student.getId(), courseId);
    }
}