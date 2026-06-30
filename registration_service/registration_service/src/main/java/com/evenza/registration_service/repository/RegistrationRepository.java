package com.evenza.registration_service.repository;

import com.evenza.registration_service.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository <Registration , Integer> {
}
