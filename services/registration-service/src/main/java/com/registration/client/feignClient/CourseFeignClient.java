package com.registration.client.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.registration.client.config.FeignClientConfig;
import com.registration.client.fallback.CourseFeignFallbackFactory;
import com.registration.dto.CourseDTO;

@FeignClient(name="course-service",
             configuration=FeignClientConfig.class,
             fallbackFactory=CourseFeignFallbackFactory.class
            )
public interface CourseFeignClient {

    @GetMapping("/courses/{courseId}")
    public CourseDTO getCourse(@PathVariable int courseId);
}
