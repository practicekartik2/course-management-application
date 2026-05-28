package com.registration.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.registration.client.feignClient.CourseFeignClient;
import com.registration.client.feignClient.StudentFeignClient;
import com.registration.dto.CourseDTO;
import com.registration.dto.RegistrationRequestDTO;
import com.registration.dto.RegistrationResponseDTO;
import com.registration.dto.RegistrationUpdateDTO;
import com.registration.dto.StudentDTO;
import com.registration.dto.ValidationResult;
import com.registration.entity.Registration;
import com.registration.exception.BusinessException;
import com.registration.exception.RegistrationException;
import com.registration.repository.RegistrationRepository;
import com.registration.validation.RegistrationValidation;


@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService{

    public final RegistrationValidation registrationValidation;

    public final RegistrationRepository registrationRepository;

    public final StudentFeignClient studentFeignClient;

    public final CourseFeignClient courseFeignClient;

    public RegistrationServiceImpl(
        RegistrationRepository registrationRepository,
        StudentFeignClient studentFeignClient, 
        CourseFeignClient courseFeignClient,
        RegistrationValidation registrationValidation)
        {    
        this.registrationRepository=registrationRepository;
        this.studentFeignClient=studentFeignClient;
        this.courseFeignClient=courseFeignClient;
        this.registrationValidation= registrationValidation;
        }
   
    @Override
    public RegistrationResponseDTO registration(RegistrationRequestDTO requestDTO) {
        
        ValidationResult result=registrationValidation.validate(requestDTO);

        if (result.getStudent() == null) {
            throw new BusinessException("Invalid student");
        }

        if (result.getCourse() == null) {
            throw new BusinessException("Invalid course");
        }
        Registration registration= new Registration();
        registration.setCourseId(requestDTO.getCourseId());
        registration.setStudentId(requestDTO.getStudentId());
        registration.setRegistrationDate(LocalDate.now());
        registration.setStartDate(requestDTO.getStartDate());
        registration.setEndDate(requestDTO.getEndDate());
        registration.setSlot(requestDTO.getSlot());
        Registration savedRegistration=registrationRepository.save(registration);
        
       return convertToResponseDTO(savedRegistration, result.getStudent(),result.getCourse());
       
    }

    @Override
    public RegistrationResponseDTO getRegistrationById(int registrationId) {
        
        Registration reg=registrationRepository.findById(registrationId)
                        .orElseThrow(()->
                        new RegistrationException("Registration not found with id: "+registrationId));


        StudentDTO student=studentFeignClient.getStudent(reg.getStudentId());
        CourseDTO course=courseFeignClient.getCourse(reg.getCourseId());
       
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
    
    private RegistrationResponseDTO convertToResponseDTO(
            Registration registration,
            StudentDTO student,
            CourseDTO course) {

        RegistrationResponseDTO dto = new RegistrationResponseDTO();

        dto.setRegistrationId(registration.getRegistrationId());
        dto.setStudentId(student.getStudentId());
        dto.setStudentName(student.getStudentName());
        dto.setCourseId(course.getCourseId());
        dto.setCourseName(course.getCourseName());
        dto.setRegistrationDate(registration.getRegistrationDate());
        dto.setStartDate(registration.getStartDate());
        dto.setEndDate(registration.getEndDate());
        dto.setSlot(registration.getSlot());

        return dto;
    }

    @Override
    public List<RegistrationResponseDTO> getAllRegistrations() {
        
       List<Registration> registrations=registrationRepository.findAll();
       return registrations.stream()
                .map(reg -> {
                    StudentDTO student =studentFeignClient.getStudent(reg.getStudentId());
                    CourseDTO course=courseFeignClient.getCourse(reg.getCourseId());
                    return convertToResponseDTO(reg, student, course);
                })
                .collect(Collectors.toList());            
    }

    @Override
    public List<RegistrationResponseDTO> getRegistrationsByStudentId(int studentId) {
        
        List<Registration> registrations=registrationRepository.findByStudentId(studentId);
        StudentDTO student=studentFeignClient.getStudent(studentId);
        return registrations.stream()
                .map(reg ->{
                    CourseDTO course=courseFeignClient.getCourse(reg.getCourseId());
                    return convertToResponseDTO(reg, student, course);
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<RegistrationResponseDTO> getRegistrationsByCourseId(int courseId) {
        
        List<Registration> registrations=registrationRepository.findByCourseId(courseId);

        CourseDTO course=courseFeignClient.getCourse(courseId);

        return registrations.stream()
                .map(reg ->{
                    StudentDTO student=studentFeignClient.getStudent(reg.getStudentId());
                    return convertToResponseDTO(reg, student, course);
                })
                .collect(Collectors.toList());

    }

    @Override
    public RegistrationResponseDTO updateRegistration(int registrationId, RegistrationUpdateDTO updateDTO) {

        Registration existing=registrationRepository.findById(registrationId)
                                    .orElseThrow(() ->
                                    new RegistrationException("Registration not found with Id: "+registrationId));

        StudentDTO student=studentFeignClient.getStudent(existing.getStudentId());

        CourseDTO course=courseFeignClient.getCourse(existing.getCourseId());

        registrationValidation.validateUpdate(existing, updateDTO);

        existing.setStartDate(updateDTO.getStartDate());
        existing.setEndDate(updateDTO.getEndDate());
        existing.setSlot(updateDTO.getSlot());

        Registration saved=registrationRepository.save(existing);
        
        return convertToResponseDTO(saved, student, course);
               
    }


    @Override
    public String cancleRegistration(int registrationId) {
        if(registrationRepository.findById(registrationId).isEmpty()){
            return"Registration does not exist";
        }
    registrationRepository.deleteById(registrationId);
        return "Registration cancelled";
        
    }
   
}