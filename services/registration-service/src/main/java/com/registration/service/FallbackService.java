package com.registration.service;



import com.registration.dto.CourseRequestDTO;
import com.registration.dto.StudentRequestDTO;


public class FallbackService {

    public StudentRequestDTO getStudentFallback(int studentId){
        StudentRequestDTO dto=new StudentRequestDTO();
        dto.setStudentId(studentId);
        dto.setStudentName("Studnet info temporarily unavailable");
        return dto;
    }

    public CourseRequestDTO getCourseFallback(int courseId) {

        CourseRequestDTO dto = new CourseRequestDTO();
        dto.setCourseId(courseId);
        dto.setCourseName("Course info temporarily unavailable");

        return dto;
    }
}
