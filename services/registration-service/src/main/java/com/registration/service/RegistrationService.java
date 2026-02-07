package com.registration.service;

import com.registration.dto.RegistrationRequestDTO;
import com.registration.dto.RegistrationResponseDTO;

public interface RegistrationService {
    public RegistrationResponseDTO registration(RegistrationRequestDTO requestDTO);
    public RegistrationResponseDTO getRegistrationById(int registrationId);
}