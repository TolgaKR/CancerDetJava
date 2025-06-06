package com.medai.medaisystem.controller;

import com.medai.medaisystem.model.Patient;
import com.medai.medaisystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @PostMapping("/kayit")
    public Patient hastaKaydet(@RequestBody Patient patient) {
        return patientService.hastaKaydet(patient);
    }


    @GetMapping("/{id}")
    public Optional<Patient> hastaGetir(@PathVariable Long id) {
        return patientService.hastaGetir(id);
    }


    @GetMapping("/doctor/{doctorId}")
    public List<Patient> doktorunHastalari(@PathVariable Long doctorId) {
        return patientService.doktorunHastalari(doctorId);
    }
}
