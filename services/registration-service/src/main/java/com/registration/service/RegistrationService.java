package com.registration.service;

import java.util.List;

import com.registration.dto.RegistrationRequestDTO;
import com.registration.dto.RegistrationResponseDTO;
import com.registration.dto.RegistrationUpdateDTO;

public interface RegistrationService {
    public RegistrationResponseDTO registration(RegistrationRequestDTO requestDTO);
    public RegistrationResponseDTO getRegistrationById(int registrationId);
    public List<RegistrationResponseDTO> getAllRegistrations();
    public List<RegistrationResponseDTO> getRegistrationsByStudentId(int studentId);
    public List<RegistrationResponseDTO> getRegistrationsByCourseId(int courseId);
    public RegistrationResponseDTO updateRegistration(int registrationId, RegistrationUpdateDTO updateDTO);
    public String cancleRegistration(int registrationId);
}