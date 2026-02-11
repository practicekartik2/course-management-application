package com.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.registration.dto.CourseRequestDTO;
import com.registration.dto.StudentRequestDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


public class ExternalServiceClient {

    @Autowired
    public RestTemplate restTemplate;

    @Retry(name = "studentService")
    public StudentRequestDTO callStudentServiceWithRetry(int studentId) {
        return callStudentService(studentId);
    }

    @CircuitBreaker(name = "studentService", fallbackMethod = "studentFallback")
    public StudentRequestDTO callStudentService(int studentId) {

        System.out.println("Calling student service...");

        return restTemplate.getForObject(
            "http://student-service/students/" + studentId,
            StudentRequestDTO.class
        );
    }


    @Retry(name = "courseService")
    public CourseRequestDTO callCourseServiceWithRetry(int courseId) {
        return callCourseServic(courseId);
    }
    
    @CircuitBreaker(name="courseService", fallbackMethod = "courseFallback")
    public CourseRequestDTO callCourseServic(int courseId){
        System.out.println("Calling course service...");
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
