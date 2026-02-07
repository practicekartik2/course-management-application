package com.student.service;

import com.student.dto.StudentRequestDTO;
import com.student.dto.StudentResponseDTO;

public interface StudentService {

    public StudentResponseDTO addStudent(StudentRequestDTO requestDTO);
    public StudentResponseDTO getStudent(int studentId);
    
}
