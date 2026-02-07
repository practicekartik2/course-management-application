package com.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registration.entity.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    
}