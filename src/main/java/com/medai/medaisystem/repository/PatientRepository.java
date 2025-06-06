package com.medai.medaisystem.repository;

import com.medai.medaisystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDoctorId(Long doctorId);

}
