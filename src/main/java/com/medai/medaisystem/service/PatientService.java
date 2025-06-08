package com.medai.medaisystem.service;

import com.medai.medaisystem.dto.PatientDto;
import com.medai.medaisystem.model.Doctor;
import com.medai.medaisystem.model.Patient;
import com.medai.medaisystem.repository.DoctorRepository;
import com.medai.medaisystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository; // <- BUNU EKLEDİK

    // DTO ile hasta kaydetme
    public Patient hastaKaydetDto(PatientDto dto) {
        Patient patient = new Patient();
        patient.setAd(dto.getAd());
        patient.setSoyad(dto.getSoyad());
        patient.setDogumTarihi(dto.getDogumTarihi());

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));

        patient.setDoctor(doctor);

        return patientRepository.save(patient);
    }

    public Optional<Patient> hastaGetir(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> doktorunHastalari(Long doctorId) {
        return patientRepository.findByDoctorId(doctorId);
    }
}
