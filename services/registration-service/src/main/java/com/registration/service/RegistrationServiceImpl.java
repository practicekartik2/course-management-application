package com.registration.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.registration.dto.CourseRequestDTO;
import com.registration.dto.RegistrationRequestDTO;
import com.registration.dto.RegistrationResponseDTO;
import com.registration.dto.StudentRequestDTO;
import com.registration.entity.Registration;
import com.registration.exception.RegistrationException;
import com.registration.repository.RegistrationRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public RegistrationRepository registrationRepository;

    @Override
    public RegistrationResponseDTO registration(RegistrationRequestDTO requestDTO) {
        
        StudentRequestDTO student;
        try {
            student=restTemplate.getForObject("http://STUDENT-SERVICE/students/"+requestDTO.getStudentId(),
                StudentRequestDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Student not found with id: "+requestDTO.getStudentId());
        }

        CourseRequestDTO course;
        try {
            course=restTemplate.getForObject("http://COURSE-SERVICE/courses/"+requestDTO.getCourseId(),
                CourseRequestDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Course not found with id: "+requestDTO.getCourseId());
        }

        Registration registration= new Registration();
        registration.setCourseId(requestDTO.getCourseId());
        registration.setStudentId(requestDTO.getStudentId());
        registration.setRegistrationDate(LocalDate.now());
        registration.setStartDate(requestDTO.getStartDate());
        registration.setEndDate(requestDTO.getEndDate());
        registration.setSlot(requestDTO.getSlot());
        Registration savedRegistration=registrationRepository.save(registration);
        return convertToResponseDTO(savedRegistration);
    }

    @Override
    public RegistrationResponseDTO getRegistrationById(int registrationId) {
        // TODO Auto-generated method stub
        Registration reg=registrationRepository.findById(registrationId)
                        .orElseThrow(()->
                        new RegistrationException("Registration not found with id: "+registrationId));

        StudentRequestDTO student;

        try {
            student=restTemplate.getForObject("http://STUDENT-SERVICE/students/"+reg.getStudentId(), 
                    StudentRequestDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Student not found with id: "+reg.getStudentId());
        }

        CourseRequestDTO course;

        try {
            course=restTemplate.getForObject("http://COURSE-SERVICE/courses/"+reg.getCourseId(), 
                    CourseRequestDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Course not found with id: "+reg.getCourseId());
        }
       
        RegistrationResponseDTO response=new RegistrationResponseDTO();
        response.setRegistrationId(reg.getRegistrationId());
        response.setStudentId(reg.getStudentId());
        response.setStudentName(student.getStudentName());
        response.setCourseId(reg.getCourseId());
        response.setCourseName(course.getCourseName());
        response.setRegistrationDate(reg.getRegistrationDate());
        response.setStartDate(reg.getStartDate());
        response.setEndDate(reg.getEndDate());
        response.setSlot(reg.getSlot());

        return response;
    }
    
    public RegistrationResponseDTO convertToResponseDTO(Registration registration){
        RegistrationResponseDTO responseDTO=new RegistrationResponseDTO();
        responseDTO.setRegistrationId(registration.getRegistrationId());
        responseDTO.setCourseId(registration.getCourseId());
        responseDTO.setStudentId(registration.getStudentId());
        responseDTO.setRegistrationDate(registration.getRegistrationDate());
        responseDTO.setStartDate(registration.getStartDate());
        responseDTO.setEndDate(registration.getEndDate());
        responseDTO.setSlot(registration.getSlot());
        return  responseDTO;
    }
}