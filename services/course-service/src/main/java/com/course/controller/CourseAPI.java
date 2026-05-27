package com.course.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.dto.CourseRequestDTO;
import com.course.dto.CourseResponseDTO;
import com.course.dto.CourseUpdateDTO;
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
    public ResponseEntity<CourseResponseDTO> addCourse(@Valid @RequestBody CourseRequestDTO courseDTO) {
        CourseResponseDTO course = courseService.addCourse(courseDTO);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable int courseId) {
        CourseResponseDTO course = courseService.getCourseById(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
               
    }

    @GetMapping("/")
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses(){
        List<CourseResponseDTO> courses=courseService.getAllCourse();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> updateCoure(@Valid @RequestBody CourseUpdateDTO courseUpdateDTO,@PathVariable int courseId){
        CourseResponseDTO course=courseService.updateCourseDetail(courseUpdateDTO, courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    } 

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable int courseId){
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("Course Deleted Successfully.",HttpStatus.OK);
    }

}
