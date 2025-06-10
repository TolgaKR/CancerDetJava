package com.medai.medaisystem.controller;

import com.medai.medaisystem.dto.PatientDto;
import com.medai.medaisystem.model.Doctor;
import com.medai.medaisystem.model.Patient;
import com.medai.medaisystem.repository.DoctorRepository;
import com.medai.medaisystem.service.DoctorService;
import com.medai.medaisystem.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin
public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService, DoctorRepository doctorRepository) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.doctorRepository = doctorRepository;
    }


    @PostMapping
    public Patient hastaKaydet(@RequestBody PatientDto patientDto, HttpSession session) {
        String email = (String) session.getAttribute("doctorEmail");

        if (email == null) {
            throw new RuntimeException("Oturumda doktor bilgisi yok, önce login ol");
        }

        Doctor doktor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));

        patientDto.setDoctorId(doktor.getId());

        return patientService.hastaKaydetDto(patientDto);
    }


    @GetMapping("/{id}")
    public Optional<Patient> hastaGetir(@PathVariable Long id) {
        return patientService.hastaGetir(id);
    }

    @GetMapping("/my")
    public List<Patient> doktorunKendiHastalari(HttpSession session) {
        String email = (String) session.getAttribute("doctorEmail");

        if (email == null) {
            throw new RuntimeException("Oturumda doktor bilgisi yok, önce login ol");
        }

        Doctor doktor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));

        return patientService.doktorunHastalari(doktor.getId());
    }
}
