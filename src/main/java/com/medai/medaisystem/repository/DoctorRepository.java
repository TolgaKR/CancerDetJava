package com.medai.medaisystem.repository;

import com.medai.medaisystem.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email); // bu çok kritik
}
