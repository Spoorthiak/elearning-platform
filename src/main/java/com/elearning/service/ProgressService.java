package com.elearning.service;

import com.elearning.entity.Progress;

import java.util.List;

public interface ProgressService {

    Progress markLessonCompleted(Long lessonId, String studentUsername);

    List<Progress> getCourseProgress(Long courseId, String studentUsername);
}