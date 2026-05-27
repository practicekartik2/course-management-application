package com.student.service;

import java.util.List;

import com.student.dto.StudentRequestDTO;
import com.student.dto.StudentResponseDTO;
import com.student.dto.StudentUpdateDTO;

public interface StudentService {

    public StudentResponseDTO addStudent(StudentRequestDTO requestDTO);

    public StudentResponseDTO getStudent(int studentId);

    public List<StudentResponseDTO> getAllStudents();

    public StudentResponseDTO updateStudentDetails(StudentUpdateDTO updateDTO,int studentId);

    public void deleteStudent(int studentId);
}
