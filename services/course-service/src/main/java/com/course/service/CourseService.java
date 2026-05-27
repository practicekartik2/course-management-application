package com.course.service;

import java.util.List;

import com.course.dto.CourseRequestDTO;
import com.course.dto.CourseResponseDTO;
import com.course.dto.CourseUpdateDTO;

public interface CourseService {

    public CourseResponseDTO addCourse(CourseRequestDTO courseDTO);

    public CourseResponseDTO getCourseById(int courseId);

    public List<CourseResponseDTO> getAllCourse();

    public CourseResponseDTO updateCourseDetail(CourseUpdateDTO courseUpdateDTO, int courseId);

    public void deleteCourse(int courseId); 

}
