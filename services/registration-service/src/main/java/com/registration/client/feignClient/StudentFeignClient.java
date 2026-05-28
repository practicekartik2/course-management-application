package com.registration.client.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.registration.client.config.FeignClientConfig;
import com.registration.client.fallback.StudentFeignFallbackFactory;
import com.registration.dto.StudentDTO;

@FeignClient(name="student-service", 
            configuration=FeignClientConfig.class,
            fallbackFactory=StudentFeignFallbackFactory.class
            )
public interface StudentFeignClient {

    @GetMapping("/students/{studentId}")
    StudentDTO getStudent(@PathVariable int studentId);
}
