package com.course.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.course.dto.CourseRequestDTO;
import com.course.dto.CourseResponseDTO;
import com.course.dto.CourseUpdateDTO;
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
    public CourseResponseDTO addCourse(CourseRequestDTO courseDTO) {
        Course course = new Course();
        course.setCourseName(courseDTO.getCourseName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());
        course.setFees(courseDTO.getFees());
        Course savedCourse = courseRepository.save(course);
        return convertToCourseDTO(savedCourse);
    }

    @Override
    public CourseResponseDTO getCourseById(int courseId) {
        return courseRepository.findById(courseId)
                .map(this::convertToCourseDTO)
                .orElseThrow(()
                        -> new CourseException("Cannot find the course with id: " + courseId));
    }

    public CourseResponseDTO convertToCourseDTO(Course course) {
        CourseResponseDTO courseDTO = new CourseResponseDTO();
        courseDTO.setCourseId(course.getCourseId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setDuration(course.getDuration());
        courseDTO.setFees(course.getFees());
        return courseDTO;
    }

    @Override
    public List<CourseResponseDTO> getAllCourse() {
       List<Course> courses=courseRepository.findAll();     
       return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDTO updateCourseDetail(CourseUpdateDTO courseUpdateDTO, int courseId) {
        Course course=courseRepository.findById(courseId)
                        .orElseThrow(()->
                             new CourseException("Course not found with Id: "+courseId));        
        course.setDuration(courseUpdateDTO.getDuration());
        course.setFees(courseUpdateDTO.getFees());
        Course updateCourse=courseRepository.save(course);
        return convertToCourseDTO(updateCourse);
    }

    @Override
    public void deleteCourse(int courseId) {
        courseRepository.deleteById(courseId);
        System.out.println("Course Deleted with Id:"+courseId);
    }
}
