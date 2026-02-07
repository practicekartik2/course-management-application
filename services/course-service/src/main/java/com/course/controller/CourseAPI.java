package com.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.dto.CourseDTO;
import com.course.service.CourseServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/courses")
public class CourseAPI {

    private final CourseServiceImpl courseService;

    public CourseAPI(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/")
    public ResponseEntity<CourseDTO> addCourse(@Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO course = courseService.addCourse(courseDTO);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable int courseId) {
        CourseDTO course = courseService.getCourseById(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
