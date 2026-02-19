package com.registration.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.registration.dto.CourseRequestDTO;
import com.registration.dto.StudentRequestDTO;

import io.github.resilience4j.retry.annotation.Retry;


public class RetryClientService {

    @Autowired
    public CircuitClientService circuitClient;

    @Retry(name="studentService")
    public StudentRequestDTO callStudentService(int studentId){
        return circuitClient.callStudent(studentId);
    }

    @Retry(name="courseService")
    public CourseRequestDTO callCourseService(int courseId){
        return circuitClient.callCourse(courseId);
    }
}
