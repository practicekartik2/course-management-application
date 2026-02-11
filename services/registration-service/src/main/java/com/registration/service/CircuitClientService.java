package com.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.registration.dto.CourseRequestDTO;
import com.registration.dto.StudentRequestDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CircuitClientService {
    @Autowired
    public RestTemplate restTemplate;

    @CircuitBreaker(name="studentService",fallbackMethod="studentFallback")
    public StudentRequestDTO callStudent(int studentId){
        System.out.println("Calling student service...");
        return restTemplate.getForObject(
                    "http://student-service/students/" + studentId, 
                        StudentRequestDTO.class);
    }

    public StudentRequestDTO studentFallback(int studentId, Exception ex){
        StudentRequestDTO dto=new StudentRequestDTO();
        dto.setStudentId(studentId);
        dto.setStudentName("Student service unavailable");
        return dto;
    }

    @CircuitBreaker(name="courseService", fallbackMethod="courseFallback")
    public CourseRequestDTO callCourse(int courseId){
        System.out.println("Calling Course service...");
        return restTemplate.getForObject(
                "http://course-service/courses/"+courseId,
                    CourseRequestDTO.class);
    }

    public CourseRequestDTO courseFallback(int courseId, Exception ex){
        CourseRequestDTO dto=new CourseRequestDTO();
        dto.setCourseId(courseId);
        dto.setCourseName("Course Service Not Available");
        return dto;
    }

}
