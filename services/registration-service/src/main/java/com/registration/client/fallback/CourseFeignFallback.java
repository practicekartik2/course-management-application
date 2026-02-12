package com.registration.client.fallback;

import org.springframework.stereotype.Component;

import com.registration.client.feignClient.CourseFeignClient;
import com.registration.dto.CourseRequestDTO;

@Component
public class CourseFeignFallback implements CourseFeignClient{

    @Override
    public CourseRequestDTO getCourse(int courseId) {
        CourseRequestDTO dto=new CourseRequestDTO();
        dto.setCourseId(courseId);
        dto.setCourseName("Course service unavailable (Course Fallback)");
        return dto;
    }

}
