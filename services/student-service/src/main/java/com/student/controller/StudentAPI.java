package com.student.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.dto.StudentRequestDTO;
import com.student.dto.StudentResponseDTO;
import com.student.service.StudentServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentAPI {

    public final StudentServiceImpl studentService;

    public StudentAPI(StudentServiceImpl studentService){
        this.studentService=studentService;
    }

    @PostMapping("/")
    public ResponseEntity<StudentResponseDTO> addStudent(@Valid @RequestBody StudentRequestDTO requestDTO){
        StudentResponseDTO responseDTO=studentService.addStudent(requestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable int studentId){
        // StudentResponseDTO student=studentService.getStudent(studentId);
        // return new ResponseEntity<>(student,HttpStatus.OK); 
        throw new RuntimeException("Failure ");
    }
}
