package com.registration.client.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.registration.client.fallback.StudentFeignFallback;
import com.registration.dto.StudentRequestDTO;

@FeignClient(name="student-service",fallback=StudentFeignFallback.class)
public interface StudentFeignClient {

    @GetMapping("/students/{studentId}")
    StudentRequestDTO getStudent(@PathVariable int studentId);
}
