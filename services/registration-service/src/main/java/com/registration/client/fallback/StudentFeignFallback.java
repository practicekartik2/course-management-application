package com.registration.client.fallback;

import org.springframework.stereotype.Component;

import com.registration.client.feignClient.StudentFeignClient;
import com.registration.dto.StudentRequestDTO;

@Component
public class StudentFeignFallback implements StudentFeignClient {

    @Override
    public StudentRequestDTO getStudent(int studentId) {
        StudentRequestDTO dto=new StudentRequestDTO();
        dto.setStudentId(studentId);
        dto.setStudentName("Student Service unavailable (Feign fallback)");
        return dto;
    }

    
}
