package com.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.registration.dto.CourseRequestDTO;
import com.registration.dto.StudentRequestDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ExternalServiceClient {

    @Autowired
    public RestTemplate restTemplate;

    @CircuitBreaker(name = "studentService", fallbackMethod = "studentFallback")
    public StudentRequestDTO callStudentService(int studentId){
        return restTemplate.getForObject("http://student-service/students/"+studentId, StudentRequestDTO.class);
    }

    @CircuitBreaker(name="courseService", fallbackMethod = "courseFallback")
    public CourseRequestDTO callCourseServic(int courseId){
        return restTemplate.getForObject("http://course-service/courses/"+courseId, CourseRequestDTO.class);
    }

    public StudentRequestDTO studentFallback(int studentId, Exception ex){
        StudentRequestDTO dto=new StudentRequestDTO();
        dto.setStudentId(studentId);
        dto.setStudentName("Student service unavailable");
        return dto;
    }

    public CourseRequestDTO courseFallback(int courseId, Exception ex){
        CourseRequestDTO dto=new CourseRequestDTO();
        dto.setCourseId(courseId);
        dto.setCourseName("Course service unavailable");
        return dto;

    }
}
