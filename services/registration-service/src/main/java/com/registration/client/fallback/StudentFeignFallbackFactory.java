package com.registration.client.fallback;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.registration.client.feignClient.StudentFeignClient;
import com.registration.dto.StudentDTO;
import com.registration.exception.NotFoundException;

@Component
public class StudentFeignFallbackFactory implements FallbackFactory<StudentFeignClient> {

    private static final Logger log=LoggerFactory.getLogger(StudentFeignFallbackFactory.class);

    @Override
    public StudentFeignClient create(Throwable cause){
        return id -> {
            if (cause instanceof NotFoundException){
                throw (NotFoundException) cause;
            }

            return buildFallbackStudent(id, cause);
        };
    }

    private StudentDTO buildFallbackStudent(int id, Throwable cause){
        log.error("Student service failed for id {}. Reason: {}",id, cause.getMessage());

        StudentDTO dto = new StudentDTO();
        dto.setStudentId(id);
        dto.setStudentName("Student service unavailable");
        

        return dto;
    }
}
