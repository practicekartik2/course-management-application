package com.registration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registration.dto.RegistrationRequestDTO;
import com.registration.dto.RegistrationResponseDTO;
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
}