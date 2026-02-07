package com.course.service;

import com.course.dto.CourseDTO;

public interface CourseService {

    public CourseDTO addCourse(CourseDTO courseDTO);

    public CourseDTO getCourseById(int courseId);

}
