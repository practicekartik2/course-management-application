package com.registration.controller;

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

import com.registration.dto.RegistrationRequestDTO;
import com.registration.dto.RegistrationResponseDTO;
import com.registration.dto.RegistrationUpdateDTO;
import com.registration.service.RegistrationServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/registrations")
public class RegistrationAPI {

        private final RegistrationServiceImpl registrationService;

        public RegistrationAPI(RegistrationServiceImpl registrationService){
            this.registrationService=registrationService;
        }
        
        @PostMapping("/")
        public ResponseEntity<RegistrationResponseDTO> registration(@Valid @RequestBody RegistrationRequestDTO requestDTO){
            RegistrationResponseDTO register=registrationService.registration(requestDTO);
            return new ResponseEntity<>(register, HttpStatus.CREATED);
        }

        @GetMapping("/{registrationId}")
        public ResponseEntity<RegistrationResponseDTO> getRegistrationById(@PathVariable int registrationId){
            RegistrationResponseDTO registration=registrationService.getRegistrationById(registrationId);
            return new ResponseEntity<>(registration, HttpStatus.OK);
        }

        @GetMapping("/")
        public ResponseEntity<List<RegistrationResponseDTO>> getAllRegistrations(){
            List<RegistrationResponseDTO> registrations=registrationService.getAllRegistrations();
            return new ResponseEntity<>(registrations,HttpStatus.OK);
            
        }

        @GetMapping("/student/{studentId}")
        public ResponseEntity<List<RegistrationResponseDTO>> getAllRegistrationsByStudentId(@PathVariable int studentId){
            List<RegistrationResponseDTO> registrations=registrationService.getRegistrationsByStudentId(studentId);
            return new ResponseEntity<>(registrations,HttpStatus.OK);
        }

        @GetMapping("/course/{courseId}")
        public ResponseEntity<List<RegistrationResponseDTO>> getAllRegistrationsByCourseId(@PathVariable int courseId){
            List<RegistrationResponseDTO> registrations=registrationService.getRegistrationsByCourseId(courseId);
            return new ResponseEntity<>(registrations, HttpStatus.OK);
        }

        @PutMapping("/{registrationId}")
        public ResponseEntity<RegistrationResponseDTO> updateRegistration(@PathVariable int registrationId,@Valid @RequestBody RegistrationUpdateDTO updateDTO){
            RegistrationResponseDTO registration=registrationService.updateRegistration(registrationId, updateDTO);
            return new ResponseEntity<>(registration, HttpStatus.OK);
        }

        @DeleteMapping("/{registrationId}")
        public ResponseEntity<String> cancelRegistration(@PathVariable int registrationId){
            registrationService.cancleRegistration(registrationId);
            return new ResponseEntity<>("Registration cancelled.", HttpStatus.OK);
        }
}