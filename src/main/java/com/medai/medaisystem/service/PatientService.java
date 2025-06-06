package com.medai.medaisystem.service;

import com.medai.medaisystem.model.Patient;
import com.medai.medaisystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient hastaKaydet(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> hastaGetir(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> doktorunHastalari(Long doctorId)
    {
        return patientRepository.findByDoctorId(doctorId);
    }


}
