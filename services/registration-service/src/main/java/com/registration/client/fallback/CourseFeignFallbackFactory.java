package com.registration.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.registration.client.feignClient.CourseFeignClient;
import com.registration.dto.CourseDTO;
import com.registration.exception.NotFoundException;

@Component
public class CourseFeignFallbackFactory implements FallbackFactory<CourseFeignClient>{

    private static final Logger log=LoggerFactory.getLogger(CourseFeignFallbackFactory.class);

    @Override
    public CourseFeignClient create (Throwable cause){
        return id -> {
            if(cause instanceof NotFoundException){
                throw (NotFoundException) cause;
            }

            return buildFactoryCourse(id, cause);
        
        };
    }

    private CourseDTO buildFactoryCourse(int id, Throwable cause){

        log.error("Student service filled for id {}. Reason: {}", id, cause.getMessage());

        CourseDTO dto= new CourseDTO();
        dto.setCourseId(id);
        dto.setCourseName("Course service unavailable");
        return dto;
    }
}
