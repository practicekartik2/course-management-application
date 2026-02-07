package com.student.service;

import org.springframework.stereotype.Service;

import com.student.dto.StudentRequestDTO;
import com.student.dto.StudentResponseDTO;
import com.student.entity.Student;
import com.student.exception.StudentException;
import com.student.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository=studentRepository;  
    }

    @Override
    public StudentResponseDTO addStudent(StudentRequestDTO requestDTO) {
        Student student=new Student();
        student.setStudentName(requestDTO.getStudentName());
        student.setEmail(requestDTO.getEmail());
        student.setPhoneNumber(requestDTO.getPhoneNumber());
        student.setDivison(requestDTO.getDivison());
        Student savedStudent=studentRepository.save(student);
        return convertTOResponseDTO(savedStudent);
        
    }

    @Override
    public StudentResponseDTO getStudent(int studentId) {
        return studentRepository.findById(studentId)
                .map(this::convertTOResponseDTO)
                .orElseThrow(()->
                        new StudentException("Student not found with id: "+studentId));
                
    }
    
    public StudentResponseDTO convertTOResponseDTO(Student student){
        StudentResponseDTO responseDTO=new StudentResponseDTO();
        responseDTO.setStudentId(student.getStudentId());
        responseDTO.setStudentName(student.getStudentName());
        responseDTO.setEmail(student.getEmail());
        responseDTO.setPhoneNumber(student.getPhoneNumber());
        responseDTO.setDivison(student.getDivison());

        return responseDTO;
    }
    
}