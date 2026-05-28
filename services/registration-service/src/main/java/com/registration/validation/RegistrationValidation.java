package com.registration.validation;

/*
student must present, course must present
student should not register the same course before during same duration

*/

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.registration.client.feignClient.CourseFeignClient;
import com.registration.client.feignClient.StudentFeignClient;
import com.registration.dto.CourseDTO;
import com.registration.dto.RegistrationRequestDTO;
import com.registration.dto.RegistrationUpdateDTO;
import com.registration.dto.StudentDTO;
import com.registration.dto.ValidationResult;
import com.registration.entity.Registration;
import com.registration.exception.BusinessException;
import com.registration.exception.NotFoundException;
import com.registration.exception.ServiceUnavailableException;
import com.registration.repository.RegistrationRepository;

@Component
public class RegistrationValidation {

        private final RegistrationRepository registrationRepository;
        private final CourseFeignClient courseFeignClient;
        private final StudentFeignClient studentFeignClient;

        public RegistrationValidation(RegistrationRepository registrationRepository,
                                    CourseFeignClient courseFeignClient,
                                    StudentFeignClient studentFeignClient) {
            this.registrationRepository = registrationRepository;
            this.courseFeignClient = courseFeignClient;
            this.studentFeignClient = studentFeignClient;
        }

        public ValidationResult validate(RegistrationRequestDTO requestDTO) {

        StudentDTO student = fetchStudent(requestDTO.getStudentId());
        CourseDTO course = fetchCourse(requestDTO.getCourseId());

        validateDateRange(requestDTO);
        validateDuration(course, requestDTO);
        validateRegistrationOverlap(requestDTO);
        validateSlotConflict(requestDTO);

        return new ValidationResult(student, course);
        }

        public void validateUpdate(Registration existing, RegistrationUpdateDTO updateDTO ){
            validateDateRange(updateDTO);
            CourseDTO course=fetchCourse(existing.getCourseId());
            validateDuration(course, updateDTO);
            validateRegistrationOverlap(existing, updateDTO);
            validateSlotConflict(existing, updateDTO);
        }


        private StudentDTO fetchStudent(int studentId) {
        try {
            return studentFeignClient.getStudent(studentId);

        } catch (NotFoundException ex) {
            // your custom exception (from ErrorDecoder)
            throw new BusinessException("Student not found with Id: " + studentId);

        } catch (feign.FeignException ex) {

            //  CRITICAL: handle FeignException also
            if (ex.status() == 404) {
                throw new BusinessException("Student not found with Id: " + studentId);
            }

            throw new ServiceUnavailableException(" Student Service is down");

        } catch (Exception ex) {
            throw new ServiceUnavailableException(" exception Student Service is down");
        }
    }

        private CourseDTO fetchCourse(int courseId) {
        try {
            return courseFeignClient.getCourse(courseId);

        } catch (NotFoundException ex) {
            throw new BusinessException("Course not found with Id: " + courseId);

        } catch (feign.FeignException ex) {

            if (ex.status() == 404) {
                throw new BusinessException("Course not found with Id: " + courseId);
            }

            throw new ServiceUnavailableException("Feigen Course Service is down");

        } catch (Exception ex) {
            throw new ServiceUnavailableException("Global exception Course Service is down");
        }
    }

        private void validateDateRange(RegistrationRequestDTO requestDTO){
            if(requestDTO.getStartDate().isAfter(requestDTO.getEndDate())){
                throw new BusinessException("Start date cannot be after end date ");
            }
        }
        
        private void validateDuration(CourseDTO course, RegistrationRequestDTO requestDTO){
            long registrationDuration=ChronoUnit.DAYS.between(requestDTO.getStartDate(), requestDTO.getEndDate());

            if(registrationDuration<course.getDuration()){
                throw new BusinessException("Course duration must be at least "+ course.getDuration()+" days" );
            }
        }

        private void validateRegistrationOverlap(RegistrationRequestDTO requestDTO){
            boolean exists= registrationRepository
                        .existsByStudentIdAndCourseIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                            requestDTO.getStudentId(),
                            requestDTO.getCourseId(),
                            requestDTO.getEndDate(),
                            requestDTO.getStartDate()  
                        );
            
            if(exists){
                throw new BusinessException(
                    "Student already registered for this course in overlapping period"
                );
            }
        }

        private void validateSlotConflict(RegistrationRequestDTO requestDTO){
            boolean exists=registrationRepository.existsByStudentIdAndSlotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                requestDTO.getStudentId(),
                requestDTO.getSlot(),
                requestDTO.getEndDate(),
                requestDTO.getStartDate()

            );

            if(exists){
                throw new BusinessException(
                    "Student already has registration in same slot during overlapping period"
                );
            }
        }

        private void validateDateRange(RegistrationUpdateDTO updateDTO){
            if(updateDTO.getStartDate().isAfter(updateDTO.getEndDate())){
                throw new BusinessException("Start date cannot be after end date");
            }
        }

        private void validateDuration(CourseDTO course,RegistrationUpdateDTO updateDTO){
            long registrationDuration=ChronoUnit.DAYS.between(updateDTO.getStartDate(), updateDTO.getEndDate());

            if(registrationDuration<course.getDuration()){
                throw new BusinessException("Course duration must be at least "+ course.getDuration()+" days" );
            }
        }

        private void validateRegistrationOverlap(Registration existing, RegistrationUpdateDTO updateDTO){
            boolean exists=registrationRepository.existsByStudentIdAndCourseIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndRegistrationIdNot(
                existing.getStudentId(),
                existing.getCourseId(),
                existing.getStartDate(),
                existing.getEndDate(),
                existing.getRegistrationId());

            if(exists){
                throw new BusinessException( "Student already registered for this course in overlapping period");
            }
        }

        private void validateSlotConflict(Registration existing, RegistrationUpdateDTO updateDTO){
            boolean exists=registrationRepository.existsByStudentIdAndSlotAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndRegistrationIdNot(
                    existing.getStudentId(),
                    existing.getSlot(),
                    existing.getStartDate(),
                    existing.getEndDate(),
                    existing.getRegistrationId());
                
                if(exists){
                    throw new BusinessException("Student already has registration in same slot during overlapping period");
                }
        }

}