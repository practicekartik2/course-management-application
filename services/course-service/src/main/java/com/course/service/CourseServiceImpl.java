package com.course.service;

import org.springframework.stereotype.Service;

import com.course.dto.CourseDTO;
import com.course.entity.Course;
import com.course.exception.CourseException;
import com.course.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseDTO addCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setCourseName(courseDTO.getCourseName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setFees(courseDTO.getFees());

        Course savedCourse = courseRepository.save(course);
        return convertToCourseDTO(savedCourse);
    }

    @Override
    public CourseDTO getCourseById(int courseId) {
        return courseRepository.findById(courseId)
                .map(this::convertToCourseDTO)
                .orElseThrow(()
                        -> new CourseException("Cannot find the course with id: " + courseId));

    }

    public CourseDTO convertToCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(course.getCourseId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setDuration(course.getDuration());
        courseDTO.setFees(course.getFees());
        return courseDTO;
    }

}
