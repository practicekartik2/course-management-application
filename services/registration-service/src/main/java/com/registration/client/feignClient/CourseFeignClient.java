package com.registration.client.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.registration.client.fallback.CourseFeignFallback;
import com.registration.dto.CourseRequestDTO;

@FeignClient(name="course-service", fallback=CourseFeignFallback.class)
public interface CourseFeignClient {

    @GetMapping("/courses/{courseId}")
    public CourseRequestDTO getCourse(@PathVariable int courseId);
}
