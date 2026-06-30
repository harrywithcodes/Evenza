package com.evenza.registration_service.service;

import com.evenza.registration_service.model.Registration;
import com.evenza.registration_service.repository.RegistrationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired

    private RegistrationRepository registrationRepository;

    public List<Registration> getAllRegistration() {
        return registrationRepository.findAll();
    }

    public Registration createregistration(Registration Reg) {
        return registrationRepository.save(Reg);
    }

    public void Delete(int id) {
        registrationRepository.deleteById(id);
    }
}